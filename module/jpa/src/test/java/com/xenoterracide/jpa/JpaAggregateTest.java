// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@TestPropertySource(
  properties = {
    // set to trace for parameter logging
    "logging.level.org.hibernate.orm.jdbc.bind = warn",
  }
)
public class JpaAggregateTest {

  @Autowired
  FooAggregateRepository repository;

  @Autowired
  EntityManager entityManager;

  @Autowired
  TransactionTemplate tx;

  @Test
  void overallTest() {
    var newAgg = FooAggregate.create("new");
    newAgg.addBar("new");
    tx.execute(cb -> repository.save(newAgg));

    var f0 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    assertThat(f0)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isEqualTo(newAgg)
      .satisfies(agg -> assertThat(agg.getBars()).hasSize(1))
      .extracting(Identifiable::getId, AbstractEntity::getVersion, FooAggregate::getName)
      .containsExactly(newAgg.getId(), 0, "new");

    f0.setName("updating");

    tx.execute(cb -> repository.save(f0));

    var f1 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    assertThat(f1)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> {
        assertThat(agg.getBars())
          .hasSize(1)
          .extracting(Identifiable::getId)
          .containsExactly(f0.getBars().stream().findFirst().get().getId());
      })
      .extracting(Identifiable::getId, AbstractEntity::getVersion, FooAggregate::getName)
      .containsExactly(f0.getId(), 1, "updating");

    var bar = f0.getBars().stream().findFirst().get();
    f1.changeBarName(bar.getId(), "new0");
    f1.addBar("new1");
    f1.addBar("new2");
    f1.addBar("new3");

    tx.execute(cb -> repository.save(f1));

    var f2 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    assertThat(f2)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> {
        assertThat(agg.getBars())
          .hasSize(4)
          .extracting(BarEntity::getName)
          .containsExactlyInAnyOrder("new0", "new1", "new2", "new3");
        assertThat(Hibernate.isInitialized(agg.getBars())).isTrue().describedAs("initialized");
      })
      .extracting(Identifiable::getId, AbstractEntity::getVersion, FooAggregate::getName)
      .containsExactly(f0.getId(), 1, "updating");

    var rev3 = repository.findRevisions(newAgg.getId()).getContent();

    assertThat(rev3).hasSize(3);

    f2.setName("3");
    tx.execute(cb -> repository.save(f2));

    var f3 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    assertThat(f3).isNotNull().satisfies(f -> assertThat(f.getVersion()).isEqualTo(2));

    var rev4 = repository.findRevisions(newAgg.getId()).getContent();

    assertThat(rev4).hasSize(4);

    tx.execute(cb -> {
      repository.delete(f3);
      return null;
    });

    var rev5 = repository.findRevisions(newAgg.getId()).getContent();

    assertThat(rev5)
      .hasSize(5)
      .extracting(Revision::getMetadata)
      .extracting(RevisionMetadata::getRevisionType)
      .containsExactly(
        RevisionMetadata.RevisionType.INSERT,
        RevisionMetadata.RevisionType.UPDATE,
        RevisionMetadata.RevisionType.UPDATE,
        RevisionMetadata.RevisionType.UPDATE,
        RevisionMetadata.RevisionType.DELETE
      );
  }

  @Test
  void lazyInit() {
    var saved = tx.execute(cb -> {
      var newAgg = FooAggregate.create("new");
      newAgg.addBar("new");
      return repository.save(newAgg);
    });

    assertThat(saved).isNotNull();

    var f0 = tx.execute(cb -> repository.findById(saved.getId()).orElseThrow());

    assertThat(f0).isNotNull();
    assertThat(Hibernate.isInitialized(f0.getBars())).isFalse().describedAs("initialized");
    assertThatExceptionOfType(LazyInitializationException.class).isThrownBy(() -> {
      // attempt to initialize proxy outside of transaction
      assertThat(f0.getBars()).isNotEmpty();
    });
  }

  @Test
  void noId() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> repository.save(new FooAggregate()));
  }

  @Test
  void identityMustHaveUuid() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
      var agg = new FooAggregate(new FooAggregate.Id(), "new");
      repository.save(agg);
    });
  }
}
