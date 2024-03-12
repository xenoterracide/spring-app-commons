// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JpaAggregateTest {

  @Autowired
  TestRepository repository;

  @Autowired
  EntityManager entityManager;

  @Test
  void withId() {
    var newAgg = new TestAggregate(TestAggregate.Id.create(), "new");

    repository.save(newAgg);
    entityManager.flush();
    entityManager.clear();
    var persisted = repository.findById(newAgg.getId()).orElse(null);

    assertThat(persisted)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isEqualTo(newAgg)
      .extracting(Identifiable::getId, TestAggregate::getName)
      .containsExactly(newAgg.getId(), "new");

    persisted.setName("updating");
    repository.save(persisted);
    entityManager.flush();
    entityManager.clear();
    var updated = repository.findById(newAgg.getId()).orElse(null);

    assertThat(updated)
      .isNotNull()
      .isNotSameAs(newAgg)
      .isEqualTo(newAgg)
      .extracting(Identifiable::getId, TestAggregate::getName)
      .containsExactly(persisted.getId(), "updating");
  }

  @Test
  void noId() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> repository.save(new TestAggregate()));
  }

  @Test
  void identityMustHaveUuid() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> {
      var agg = new TestAggregate(new TestAggregate.Id(), "new");
      repository.save(agg);
    });
  }
}
