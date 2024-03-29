// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

/**
 * A user.
 */
public class User {

  /**
   * for JPA
   */
  protected User() {}

  /**
   * Creates a new builder.
   *
   * @return A new builder.
   */
  public static UserBuilder builder() {
    return UserBuilder.create();
  }
}
