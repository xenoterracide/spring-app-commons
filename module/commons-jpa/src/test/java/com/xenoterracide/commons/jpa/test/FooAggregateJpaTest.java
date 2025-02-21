// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.xenoterracide.jpa.fixtures.FooAggregate;
import com.xenoterracide.jpa.fixtures.FooAggregateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles({ "test", "test-jpa" })
class FooAggregateJpaTest {

  @Autowired
  FooAggregateRepository repository;

  @Test
  void noId() {
    assertThatExceptionOfType(JpaSystemException.class).isThrownBy(() -> repository.save(new FooAggregate()));
  }
}
