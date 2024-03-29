// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import org.immutables.builder.Builder;
import org.immutables.value.Value;

@Value.Style(newBuilder = "create", jakarta = true, jdk9Collections = true, jdkOnly = true)
final class UserFactory {

  private UserFactory() {}

  @Builder.Factory
  static User user(String name) {
    return new User();
  }
}
