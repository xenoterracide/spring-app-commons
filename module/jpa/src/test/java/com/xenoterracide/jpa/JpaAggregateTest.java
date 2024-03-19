// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = "logging.level.org.hibernate.orm.jdbc.bind = trace")
public class JpaAggregateTest {

  @Autowired
  TestAggregateRepository repository;

  @Autowired
  EntityManager entityManager;

  @Test
  void overallTest() {
    var newAgg = new FooAggregate(FooAggregate.Id.create(), "new");
    newAgg.getBars().add(BarEntity.create("new"));

    repository.save(newAgg);
    entityManager.flush();
    entityManager.clear();
    var f0 = repository.findById(newAgg.getId()).orElse(null);

    assertThat(f0)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> assertThat(agg.getBars()).hasSize(1))
      .extracting(Identifiable::getId, AbstractEntity::getVersion, FooAggregate::getName)
      .containsExactly(newAgg.getId(), 1, "new");

    f0.setName("updating");

    repository.save(f0);
    entityManager.flush();
    entityManager.clear();
    var f1 = repository.findById(newAgg.getId()).orElse(null);

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
      .containsExactly(f0.getId(), 2, "updating");

    var bar = f0.getBars().stream().findFirst().get();
    f1.changeBarName(bar.getId(), "new0");
    f1.getBars().add(BarEntity.create("new1"));
    f1.getBars().add(BarEntity.create("new2"));
    f1.getBars().add(BarEntity.create("new3"));

    repository.save(f1);
    entityManager.flush();
    entityManager.clear();

    var f2 = repository.findById(newAgg.getId()).orElse(null);

    assertThat(f2)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isNotEqualTo(newAgg)
      .satisfies(agg -> {
        // fails if debugging because toString is called
        assertThat(Hibernate.isInitialized(agg.getBars())).isFalse().describedAs("initialized");
        assertThat(agg.getBars())
          .hasSize(4)
          .extracting(BarEntity::getName)
          .containsExactlyInAnyOrder("new0", "new1", "new2", "new3");
        assertThat(Hibernate.isInitialized(agg.getBars())).isTrue().describedAs("initialized");
      })
      .extracting(Identifiable::getId, AbstractEntity::getVersion, FooAggregate::getName)
      .containsExactly(f0.getId(), 3, "updating");
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
