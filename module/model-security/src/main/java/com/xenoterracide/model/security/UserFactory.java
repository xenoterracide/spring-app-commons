// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import jakarta.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.jspecify.annotations.NonNull;

@Value.Style(newBuilder = "create", jakarta = true, jdk9Collections = true, jdkOnly = true)
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(@NonNull String name, @NonNull Set<IdentityProviderUser> identityProviderUsers) {
    return new User(User.Identifier.create(), name, new HashSet<>(identityProviderUsers));
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
