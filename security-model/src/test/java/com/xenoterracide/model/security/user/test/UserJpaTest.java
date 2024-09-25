// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.model.security.user.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.xenoterracide.model.security.fixtures.UserTestDataBuilder;
import com.xenoterracide.model.security.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles({ "test", "test-jpa" })
class UserJpaTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  EntityManager em;

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
