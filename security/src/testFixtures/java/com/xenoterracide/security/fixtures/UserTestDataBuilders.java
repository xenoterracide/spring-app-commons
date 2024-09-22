// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.security.fixtures;

import com.xenoterracide.security.user.IdentityProviderUser;
import com.xenoterracide.security.user.IdentityProviderUserBuilder;
import com.xenoterracide.security.user.User;
import com.xenoterracide.security.user.UserBuilder;
import java.util.Optional;
import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Value.Style(typeBuilder = "*TestDataBuilder", newBuilder = "create", jdkOnly = true, jdk9Collections = true)
final class UserTestDataBuilders {

  private UserTestDataBuilders() {}

  @Builder.Factory
  static User user(
    @NonNull Optional<String> name,
    @NonNull Optional<IdentityProviderUser.IdP> idP,
    @NonNull Optional<String> idPUserId
  ) {
    var user = UserBuilder.create().name(name.orElse("xeno")).build();

    user.linkIdentityProvider(idP.orElse(IdentityProviderUser.IdP.AUTH0), idPUserId.orElse("1234"));
    return user;
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    @NonNull Optional<IdentityProviderUser.IdP> idP,
    @NonNull Optional<String> idPUserId,
    @NonNull Optional<User> user
  ) {
    var optIdP = idP.orElse(IdentityProviderUser.IdP.AUTH0);
    var optIdPUserId = idPUserId.orElse("1234");

    var ub = IdentityProviderUserBuilder.create()
      .idP(optIdP)
      .idPUserId(optIdPUserId)
      .user(user.orElseGet(() -> UserBuilder.create().name("xeno").build()));
    return ub.build();
  }
}
