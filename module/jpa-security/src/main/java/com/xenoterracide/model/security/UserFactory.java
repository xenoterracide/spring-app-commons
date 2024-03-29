// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.annotation.Nonnull;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

@Value.Style(newBuilder = "create", jakarta = true, jdk9Collections = true, jdkOnly = true)
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(@Nonnull String name) {
    return new User(new User.Identifier(UuidCreator.getTimeOrderedEpoch()), name);
  }
}
