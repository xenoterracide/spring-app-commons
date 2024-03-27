// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserTest {

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

    var u1 = userRepository.findById(u0.getId());
    assertThat(u1).isPresent();

    var u2 = userRepository.findByIdentityProviderUser(u0.getIdentityProviderUsers().iterator().next().getId());
    assertThat(u2).isPresent();

    assertThat(u2.get()).isEqualTo(u1.get());
  }

  @Test
  void equality() {
    EqualsVerifier.forClass(User.class)
      .withRedefinedSuperclass()
      .suppress(Warning.SURROGATE_OR_BUSINESS_KEY)
      .withPrefabValues(
        IdentityProviderUser.class,
        IdentityProviderUserTestDataBuilder.create().build(),
        IdentityProviderUserTestDataBuilder.create().build()
      )
      .verify();
  }
}
