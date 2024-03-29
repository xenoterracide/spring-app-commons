// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.model.Identifiable;
import jakarta.annotation.Nonnull;
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
  static User user(@Nonnull Optional<String> name, @Nonnull Optional<Set<IdentityProviderUser>> identityProviderUsers) {
    var uid = new User.Identifier(UuidCreator.getTimeOrderedEpoch());
    var u = new User(
      uid,
      name.orElse("xeno"),
      identityProviderUsers.orElseGet(() -> {
        return Set.of(IdentityProviderUserTestDataBuilder.create().userId(uid).build());
      })
    );
    u.getIdentityProviderUsers().forEach(ipu -> ipu.setUser(u));
    return u;
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    @Nonnull Optional<IdentityProviderUser.IdP> idP,
    @Nonnull Optional<String> idPUserId,
    @Nonnull Optional<User.Identifier> userId,
    @Nonnull Optional<User> user
  ) {
    var optIdP = idP.orElse(IdentityProviderUser.IdP.AUTH0);
    var optIdPUserId = idPUserId.orElse("1234");

    var optUserId = user
      .map(Identifiable::getId)
      .orElseGet(() -> userId.orElseGet(() -> new User.Identifier(UuidCreator.getTimeOrderedEpoch())));

    return new IdentityProviderUser(new IdentityProviderUser.Identifier(optIdP, optIdPUserId, optUserId));
  }
}
