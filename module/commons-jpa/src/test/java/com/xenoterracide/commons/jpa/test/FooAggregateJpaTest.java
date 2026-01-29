// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.xenoterracide.jpa.fixtures.FooAggregate;
import com.xenoterracide.jpa.fixtures.FooAggregateRepository;
import io.helidon.service.registry.ServiceRegistryManager;
import io.helidon.transaction.TxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

class FooAggregateJpaTest {

  private static final ServiceRegistryManager REGISTRY_MANAGER = ServiceRegistryManager.start();

  static {
    var registry = REGISTRY_MANAGER.registry();
    var executor = registry.get(io.helidon.data.jakarta.persistence.JpaRepositoryExecutor.class);
    executor.run(em -> {
      System.out.println(
        "[DEBUG_LOG] Persistence Unit Name: " +
          em.getEntityManagerFactory().getProperties().get("jakarta.persistence.persistence-unit-name")
      );
      em
        .createNativeQuery("CREATE TABLE FOOAGGREGATE (ID UUID PRIMARY KEY, NAME VARCHAR(255), VERSION INT)")
        .executeUpdate();
      em
        .createNativeQuery(
          "CREATE TABLE BARENTITY (ID UUID PRIMARY KEY, NAME VARCHAR(255), VERSION INT, FOO_ID UUID, FOREIGN KEY (FOO_ID) REFERENCES FOOAGGREGATE(ID))"
        )
        .executeUpdate();
    });
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
      .withStackTraceContaining("jakarta.persistence.PersistenceException");
  }
}
