// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.annotation.Nonnull;
import java.util.Set;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

@Value.Style(newBuilder = "create", jakarta = true, jdk9Collections = true, jdkOnly = true)
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(@Nonnull String name, @Nonnull Set<IdentityProviderUser> identityProviderUsers) {
    return new User(new User.Identifier(UuidCreator.getTimeOrderedEpoch()), name, identityProviderUsers);
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    @Nonnull IdentityProviderUser.IdP idP,
    @Nonnull String idPUserId,
    @Nonnull User user
  ) {
    var idpUser = new IdentityProviderUser(new IdentityProviderUser.Identifier(idP, idPUserId, user.getId()));
    idpUser.setUser(user);
    return idpUser;
  }
}
