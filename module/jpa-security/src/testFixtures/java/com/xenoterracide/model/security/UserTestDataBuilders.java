// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.tools.java.collection.CollectionTools;
import jakarta.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Value.Style(
  typeBuilder = "*TestDataBuilder",
  newBuilder = "create",
  jakarta = true,
  jdkOnly = true,
  jdk9Collections = true
)
final class UserTestDataBuilders {

  private UserTestDataBuilders() {}

  @Builder.Factory
  static User user(@Nonnull Optional<String> name, @Nonnull Set<IdentityProviderUser> identityProviderUsers) {
    var u = new User(
      new User.Identifier(UuidCreator.getTimeOrderedEpoch()),
      name.orElse("xeno"),
      identityProviderUsers
    );
    CollectionTools.addIf(
      u.getIdentityProviderUsers(),
      IdentityProviderUserTestDataBuilder.create().user(u).build(),
      Collection::isEmpty
    );
    u.getIdentityProviderUsers().forEach(ipu -> ipu.setUser(u));
    return u;
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    @Nonnull Optional<IdentityProviderUser.IdP> idP,
    @Nonnull Optional<String> idPUserId,
    @Nonnull Optional<User> user
  ) {
    var optIdP = idP.orElse(IdentityProviderUser.IdP.AUTH0);
    var optIdPUserId = idPUserId.orElse("1234");
    var optUser = user.orElseGet(() -> UserTestDataBuilder.create().build());
    return new IdentityProviderUser(
      new IdentityProviderUser.Identifier(optIdP, optIdPUserId, optUser.getId()),
      optUser
    );
  }
}
