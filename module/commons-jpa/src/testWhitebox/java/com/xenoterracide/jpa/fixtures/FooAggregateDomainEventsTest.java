// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.jpa.fixtures;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FooAggregateDomainEventsTest {

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
