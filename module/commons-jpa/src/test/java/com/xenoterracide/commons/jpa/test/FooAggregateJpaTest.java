// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.xenoterracide.jpa.fixtures.FooAggregate;
import com.xenoterracide.jpa.fixtures.FooAggregateRepository;
import io.helidon.data.jakarta.persistence.JpaRepositoryExecutor;
import io.helidon.service.registry.ServiceRegistryManager;
import io.helidon.transaction.TxException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FooAggregateJpaTest {

  private static final ServiceRegistryManager REGISTRY_MANAGER = ServiceRegistryManager.start();
  private static final Logger log = LogManager.getLogger(FooAggregateJpaTest.class);

  @BeforeAll
  static void beforeAll() {
    var registry = REGISTRY_MANAGER.registry();
    var executor = registry.get(JpaRepositoryExecutor.class);
    executor.run(em -> log.info("EntityManager Properties: {}", em.getEntityManagerFactory().getProperties()));
  }

  FooAggregateRepository repository = REGISTRY_MANAGER.registry().get(FooAggregateRepository.class);

  @AfterAll
  static void afterAll() {
    REGISTRY_MANAGER.shutdown();
  }

  @Test
  void success() {
    var foo = FooAggregate.create("foo");
    var created = repository.save(foo);

    assertThat(created).isNotNull();
    assertThat(created.getId()).isEqualTo(foo.getId());
  }

  @Test
  void noId() {
    assertThatExceptionOfType(TxException.class)
      .isThrownBy(() -> repository.save(new FooAggregate()))
      .withStackTraceContaining(
        "Identifier of entity '%s' must be manually assigned before calling 'persist()'",
        FooAggregate.class.getCanonicalName()
      );
  }
}
