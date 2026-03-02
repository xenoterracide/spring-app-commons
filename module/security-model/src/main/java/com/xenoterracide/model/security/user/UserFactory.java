// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.immutables.builder.Builder;
import org.jmolecules.architecture.layered.InfrastructureLayer;

/**
 * Do not use directly, this class is for generating builders that you should use instead.
 */
@InfrastructureLayer
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(String name, Set<IdentityProviderUser> identityProviderUsers) {
    return new User(User.UserId.create(), name, new HashSet<>(identityProviderUsers));
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    URI issuer,
    String subject,
    String email,
    boolean emailVerified,
    User user
  ) {
    var idpUser = new IdentityProviderUser(
      new IdentityProviderUser.IdentityProviderUserId(issuer, subject, user.getId())
    );
    idpUser.setUser(user);
    idpUser.setEmail(email);
    idpUser.setEmailVerified(emailVerified);
    return idpUser;
  }
}
