// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.jpa.fixtures;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.xenoterracide.commons.jpa.AbstractSurrogateEntity_;
import com.xenoterracide.commons.model.Identifiable;
import jakarta.validation.ConstraintViolationException;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

@ActiveProfiles({ "test", "test-jpa" })
@SpringBootTest
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@TestPropertySource(
  properties = {
    // set to trace for parameter logging
    "logging.level.org.hibernate.orm.jdbc.bind = warn",
  }
)
class FooAggregateTransactionTest {

  @Autowired
  FooAggregateRepository repository;

  @Autowired
  TransactionTemplate tx;

  @Test
  @SuppressWarnings("NullAway")
  void overallTest() {
    var newAgg = FooAggregate.create("new");
    newAgg.addBar("new");
    tx.execute(cb -> repository.saveAndFlush(newAgg));

    var f0 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    Assertions.assertThat(f0)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isEqualTo(newAgg)
      .satisfies(agg -> Assertions.assertThat(agg.getBars()).hasSize(1))
      .extracting(Identifiable::getId, FooAggregate::getName)
      .containsExactly(newAgg.getId(), "new");

    f0.setName("updating");

    tx.execute(cb -> repository.saveAndFlush(f0));

    var f1 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    Assertions.assertThat(f1)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> {
        Assertions.assertThat(agg.getBars())
          .hasSize(1)
          .extracting(Identifiable::getId)
          .containsExactly(f0.getBars().stream().findFirst().get().getId());
      })
      .hasFieldOrPropertyWithValue(AbstractSurrogateEntity_.VERSION, 1)
      .extracting(Identifiable::getId, FooAggregate::getName)
      .containsExactly(f0.getId(), "updating");

    var bar = f0.getBars().stream().findFirst().get();
    f1.changeBarName(bar.getId(), "new0");
    f1.addBar("new1");
    f1.addBar("new2");
    f1.addBar("new3");

    tx.execute(cb -> repository.saveAndFlush(f1));

    var f2 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    Assertions.assertThat(f2)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> {
        Assertions.assertThat(agg.getBars())
          .hasSize(4)
          .extracting(BarEntity::getName)
          .containsExactlyInAnyOrder("new0", "new1", "new2", "new3");
        Assertions.assertThat(Hibernate.isInitialized(agg.getBars())).isTrue().describedAs("initialized");
      })
      .hasFieldOrPropertyWithValue(AbstractSurrogateEntity_.VERSION, 1)
      .extracting(Identifiable::getId, FooAggregate::getName)
      .containsExactly(f0.getId(), "updating");

    var rev3 = repository.findRevisions(newAgg.getId()).getContent();

    Assertions.assertThat(rev3).hasSize(3);

    f2.setName("3");
    tx.execute(cb -> repository.saveAndFlush(f2));

    var f3 = tx.execute(cb -> repository.findOneById(newAgg.getId()));

    Assertions.assertThat(f3).isNotNull().hasFieldOrPropertyWithValue(AbstractSurrogateEntity_.VERSION, 2);

    var rev4 = repository.findRevisions(newAgg.getId()).getContent();

    Assertions.assertThat(rev4).hasSize(4);

    tx.execute(cb -> {
      repository.delete(f3);
      return null;
    });

    var rev5 = repository.findRevisions(newAgg.getId()).getContent();

    Assertions.assertThat(rev5)
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

    Assertions.assertThat(saved).isNotNull();

    var f0 = tx.execute(cb -> repository.findById(saved.getId()).orElseThrow());

    Assertions.assertThat(f0).isNotNull();
    Assertions.assertThat(Hibernate.isInitialized(f0.getBars())).isFalse().describedAs("initialized");
    assertThatExceptionOfType(LazyInitializationException.class).isThrownBy(() -> {
      // attempt to initialize proxy outside of transaction
      Assertions.assertThat(f0.getBars()).isNotEmpty();
    });
  }

  @Test
  @SuppressWarnings("NullAway")
  void lazyInitFromInverse() {
    var foo = tx.execute(cb -> {
      var newAgg = FooAggregate.create("new");
      newAgg.addBar("new");
      return repository.save(newAgg);
    });

    Assertions.assertThat(foo).isNotNull();

    var saved = foo.getBars().stream().findAny().get();

    var bar = tx.execute(cb -> repository.findOneBarEntityById(saved.getId()));

    Assertions.assertThat(bar).isNotNull();
    Assertions.assertThat(Hibernate.isInitialized(bar.getFoo())).isFalse().describedAs("initialized");

    // if designed right asking for an id we already have shouldn't trigger a load
    Assertions.assertThat(bar.getFoo().getId()).isNotNull();
    Assertions.assertThat(Hibernate.isInitialized(bar.getFoo())).isFalse().describedAs("still not lazy loaded");

    Assertions.assertThat(bar).isNotSameAs(saved).isEqualTo(saved);

    // checking this way as assertThat calls toString which trigers proxy load, proxy can't be equal to non-proxy
    assertThat(Objects.equals(bar.getFoo(), foo)).isFalse().describedAs("foo equality");

    assertThatExceptionOfType(LazyInitializationException.class).isThrownBy(() -> {
      // attempt to initialize proxy outside of transaction
      Assertions.assertThat(bar.getFoo().getName()).isNotEmpty();
    });
  }

  @Test
  void identityMustHaveUuid() {
    assertThatExceptionOfType(ConstraintViolationException.class)
      .isThrownBy(() -> {
        tx.execute(cb -> {
          var agg = new FooAggregate(new FooAggregate.Id(), "new");
          return repository.saveAndFlush(agg);
        });
      })
      .withMessageContaining("propertyPath=id.value");
  }
}
