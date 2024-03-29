// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Disabled;

public class UserTest {

  @Disabled
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
