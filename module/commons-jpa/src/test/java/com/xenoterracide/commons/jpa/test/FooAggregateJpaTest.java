// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.xenoterracide.jpa.fixtures.FooAggregate;
import com.xenoterracide.jpa.fixtures.FooAggregateRepository;
import io.helidon.service.registry.Services;
import org.junit.jupiter.api.Test;

class FooAggregateJpaTest {

  FooAggregateRepository repository = Services.get(FooAggregateRepository.class);

  @Test
  void noId() {
    assertThatExceptionOfType(IllegalAccessException.class).isThrownBy(() -> repository.save(new FooAggregate()));
  }
}
