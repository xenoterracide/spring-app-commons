// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.EntityManager;
import java.util.Set;
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
    var u0 = new User(new User.Identifier(UuidCreator.getTimeOrderedEpoch()));
    u0.setName("xeno");
    u0.setIdentityProviderUsers(Set.of(new IdentityProviderUser(u0, IdentityProviderUser.IdP.AUTH0, "1234")));
    userRepository.save(u0);
    em.flush();
    em.clear();

    var u1 = userRepository.findById(u0.getId());
    assertThat(u1).isPresent();

    var u2 = userRepository.findByIdentityProviderUser(u0.getIdentityProviderUsers().iterator().next().getId());
    assertThat(u2).isPresent();

    assertThat(u2.get()).isEqualTo(u1.get());
  }
}
