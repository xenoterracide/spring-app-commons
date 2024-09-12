// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.fixtures;

import com.xenoterracide.model.security.IdentityProviderUser;
import com.xenoterracide.model.security.IdentityProviderUserBuilder;
import com.xenoterracide.model.security.User;
import com.xenoterracide.model.security.UserBuilder;
import java.util.Optional;
import org.immutables.builder.Builder;
import org.immutables.value.Value;
import org.jspecify.annotations.NonNull;

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
