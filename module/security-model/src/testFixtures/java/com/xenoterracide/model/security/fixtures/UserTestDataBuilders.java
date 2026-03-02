// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.fixtures;

import com.xenoterracide.model.security.user.IdentityProviderUser;
import com.xenoterracide.model.security.user.User;
import java.net.URI;
import java.util.Optional;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Value.Style(typeBuilder = "*TestDataBuilder", newBuilder = "create", jdkOnly = true, jdk9Collections = true)
final class UserTestDataBuilders {

  private static final URI DEFAULT_ISSUER = URI.create("https://auth0.example.com/");

  private UserTestDataBuilders() {}

  @Builder.Factory
  static User user(
    Optional<String> name,
    Optional<URI> issuer,
    Optional<String> subject,
    Optional<String> email,
    Optional<Boolean> emailVerified
  ) {
    var user = User.builder().name(name.orElse("xeno")).build();

    user.linkIdentityProvider(
      issuer.orElse(DEFAULT_ISSUER),
      subject.orElse("1234"),
      email.orElse("xeno@example.com"),
      emailVerified.orElse(true)
    );
    return user;
  }

  @Builder.Factory
  static IdentityProviderUser identityProviderUser(
    Optional<URI> issuer,
    Optional<String> subject,
    Optional<String> email,
    Optional<Boolean> emailVerified,
    Optional<User> user
  ) {
    var optIssuer = issuer.orElse(DEFAULT_ISSUER);
    var optSubject = subject.orElse("1234");
    var optEmail = email.orElse("xeno@example.com");
    var optEmailVerified = emailVerified.orElse(true);

    var ub = IdentityProviderUser.builder()
      .issuer(optIssuer)
      .subject(optSubject)
      .email(optEmail)
      .emailVerified(optEmailVerified)
      .user(user.orElseGet(() -> User.builder().name("xeno").build()));
    return ub.build();
  }
}
