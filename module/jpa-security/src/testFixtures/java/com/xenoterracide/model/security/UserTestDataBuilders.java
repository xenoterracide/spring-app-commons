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
@Value.Style(typeBuilder = "*TestDataBuilder", newBuilder = "create", jakarta = true)
final class UserTestDataBuilders {

  private UserTestDataBuilders() {}

  @Builder.Factory
  static User user(@Nonnull Optional<String> name, @Nonnull Set<IdentityProviderUser> identityProviderUsers) {
    var u = new User(new User.Identifier(UuidCreator.getTimeOrderedEpoch()));
    u.setName(name.orElse("xeno"));
    CollectionTools.addIf(
      u.getIdentityProviderUsers(),
      IdentityProviderUserTestDataBuilder.create().build(),
      Collection::isEmpty
    );
    u.getIdentityProviderUsers().forEach(ipu -> ipu.setUser(u));
    return u;
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(IdentityProviderUser.IdP idP, String idPUserId, User user) {
    var optIdP = Optional.ofNullable(idP).orElse(IdentityProviderUser.IdP.AUTH0);
    var optIdPUserId = Optional.ofNullable(idPUserId).orElse("1234");
    var optUser = Optional.ofNullable(user).orElseGet(() -> UserTestDataBuilder.create().build());
    return new IdentityProviderUser(
      new IdentityProviderUser.Identifier(optIdP, optIdPUserId, optUser.getId()),
      optUser
    );
  }
}
