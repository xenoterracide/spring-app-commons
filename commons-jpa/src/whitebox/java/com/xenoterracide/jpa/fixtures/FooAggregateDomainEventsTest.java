// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.fixtures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles({ "test", "test-jpa" })
class FooAggregateDomainEventsTest {

  @Autowired
  FooAggregateRepository repository;

  @Test
  void eventsPropagated() {
    var agg = FooAggregate.create("new");
    agg.addBar("bar").changeName("baz");
    assertThat(agg.domainEvents()).isNotEmpty();
    repository.save(agg);
    assertThat(agg.domainEvents()).isEmpty();
  }
}
