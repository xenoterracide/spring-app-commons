// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test-jpa")
class FooAggregateJpaTest {

  @Autowired
  FooAggregateRepository repository;

  @Test
  void noId() {
    assertThatExceptionOfType(JpaSystemException.class).isThrownBy(() -> repository.save(new FooAggregate()));
  }

  @Test
  void eventsPropagated() {
    var agg = FooAggregate.create("new");
    agg.addBar("bar").changeName("baz");
    assertThat(agg.domainEvents()).isNotEmpty();
    repository.save(agg);
    assertThat(agg.domainEvents()).isEmpty();
  }
}
