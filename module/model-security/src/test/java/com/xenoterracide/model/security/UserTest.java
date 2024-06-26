// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import static org.assertj.core.api.Assertions.assertThat;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  void equals() {
    assertThat(User.builder().name("xeno").build()).isNotEqualTo(User.builder().name("xeno").build());
    assertThat(
      IdentityProviderUser.builder()
        .idP(IdentityProviderUser.IdP.AUTH0)
        .idPUserId("1234")
        .user(UserTestDataBuilder.create().name("xeno").build())
        .build()
    ).isNotEqualTo(
      IdentityProviderUser.builder()
        .idP(IdentityProviderUser.IdP.AUTH0)
        .idPUserId("1234")
        .user(UserTestDataBuilder.create().name("xeno").build())
        .build()
    );
  }

  @SuppressWarnings("NullAway")
  void equality() {
    EqualsVerifier.forClass(User.class)
      .withRedefinedSuperclass()
      .suppress(Warning.SURROGATE_OR_BUSINESS_KEY)
      .withPrefabValues(
        IdentityProviderUser.class,
        IdentityProviderUserTestDataBuilder.create().build().build(),
        IdentityProviderUserTestDataBuilder.create().build().build()
      )
      .verify();
  }
}
