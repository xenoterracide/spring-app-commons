// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import java.util.HashSet;
import java.util.Set;
import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.jmolecules.architecture.layered.InfrastructureLayer;
import org.jspecify.annotations.NonNull;

/**
 * Do not use directly, this class is for generating builders that you should use instead.
 */
@InfrastructureLayer
@Value.Style(newBuilder = "create", jdk9Collections = true, jdkOnly = true)
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(@NonNull String name, @NonNull Set<IdentityProviderUser> identityProviderUsers) {
    return new User(User.UserId.create(), name, new HashSet<>(identityProviderUsers));
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    IdentityProviderUser.@NonNull IdP idP,
    @NonNull String idPUserId,
    @NonNull User user
  ) {
    var idpUser = new IdentityProviderUser(
      new IdentityProviderUser.IdentityProviderUserId(idP, idPUserId, user.getId())
    );
    idpUser.setUser(user);
    return idpUser;
  }
}
