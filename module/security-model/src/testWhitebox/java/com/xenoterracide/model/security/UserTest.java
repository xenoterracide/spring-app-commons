// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.model.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.xenoterracide.commons.jpa.AggregateIdentifier;
import com.xenoterracide.commons.model.EntityIdentifier;
import com.xenoterracide.model.security.fixtures.IdentityProviderUserTestDataBuilder;
import com.xenoterracide.model.security.fixtures.UserTestDataBuilder;
import com.xenoterracide.model.security.user.IdentityProviderUser;
import com.xenoterracide.model.security.user.User;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Disabled;
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

  @Disabled
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
      .withPrefabValues(User.class, UserTestDataBuilder.create().build(), UserTestDataBuilder.create().build())
      .withPrefabValues(
        EntityIdentifier.class,
        new AggregateIdentifier<>(User.class, User.UserId.create()),
        new AggregateIdentifier<>(User.class, User.UserId.create())
      )
      .verify();
  }
}
