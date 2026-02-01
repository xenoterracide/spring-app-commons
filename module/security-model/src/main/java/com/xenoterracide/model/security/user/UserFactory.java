// Copyright 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.model.security.user;

import java.util.ArrayList;
import java.util.List;
import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.jmolecules.architecture.layered.InfrastructureLayer;

/**
 * Do not use directly, this class is for generating builders that you should use instead.
 */
@InfrastructureLayer
@Value.Style(newBuilder = "create", jdk9Collections = true, jdkOnly = true)
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(String name, List<IdentityProviderUser> identityProviderUsers) {
    return new User(User.UserId.create(), name, new ArrayList<>(identityProviderUsers));
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(IdentityProviderUser.IdP idP, String idPUserId, User user) {
    var idpUser = new IdentityProviderUser(
      new IdentityProviderUser.IdentityProviderUserId(idP, idPUserId, user.getId())
    );
    idpUser.setUser(user);
    return idpUser;
  }
}
