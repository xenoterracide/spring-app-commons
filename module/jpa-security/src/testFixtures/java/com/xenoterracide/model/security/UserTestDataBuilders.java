// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.github.f4b6a3.uuid.UuidCreator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

@Value.Style(typeBuilder = "*TestDataBuilder", newBuilder = "create")
class UserTestDataBuilders {

  @Builder.Factory
  static User user(UUID id, String name, Set<IdentityProviderUser> identityProviderUsers) {
    var u = new User(new User.Identifier(Optional.ofNullable(id).orElseGet(UuidCreator::getTimeOrderedEpoch)));
    u.setName(Optional.ofNullable(name).orElse("xeno"));
    u.setIdentityProviderUsers(
      Optional.ofNullable(identityProviderUsers).orElseGet(
        () -> Set.of(IdentityProviderUserTestDataBuilder.create().user(u).build())
      )
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
