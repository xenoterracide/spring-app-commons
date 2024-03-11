// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class JpaAggregateTest {

  @Autowired
  TestRepository repository;

  @Test
  void testEntity() {
    var newAgg = new TestAggregate();

    var persisted = repository.save(newAgg);

    assertThat(persisted.getId()).isNotNull().isEqualTo(newAgg.getId());
  }
}
