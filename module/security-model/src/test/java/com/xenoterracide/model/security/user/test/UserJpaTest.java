// Copyright 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.model.security.user.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.xenoterracide.model.security.fixtures.UserTestDataBuilder;
import com.xenoterracide.model.security.user.UserRepository;
import io.helidon.data.jakarta.persistence.JpaRepositoryExecutor;
import io.helidon.service.registry.ServiceRegistryManager;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserJpaTest {

  static final ServiceRegistryManager REGISTRY_MANAGER = ServiceRegistryManager.start();
  static final Logger log = LogManager.getLogger(UserJpaTest.class);
  UserRepository userRepository = REGISTRY_MANAGER.registry().get(UserRepository.class);
  EntityManager em = REGISTRY_MANAGER.registry().get(EntityManager.class);

  @BeforeAll
  static void beforeAll() {
    var registry = REGISTRY_MANAGER.registry();
    var executor = registry.get(JpaRepositoryExecutor.class);
    executor.run(em -> log.info("EntityManager Properties: {}", em.getEntityManagerFactory().getProperties()));
  }

  @AfterAll
  static void afterAll() {
    REGISTRY_MANAGER.shutdown();
  }

  @Test
  void save() {
    var u0 = UserTestDataBuilder.create().build();
    userRepository.save(u0);
    em.flush();
    em.clear();

    var u1 = userRepository.findById(u0.getId()).orElseThrow();

    var u2 = userRepository
      .findByIdentityProviderUser(u1.linkedIdentityProviderUsers().iterator().next().getId())
      .orElseThrow();

    assertThat(u2).isEqualTo(u1);
  }
}
