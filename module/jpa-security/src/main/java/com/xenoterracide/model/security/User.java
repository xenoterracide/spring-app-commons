// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.jpa.AbstractIdentitifier;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

/**
 * A user.
 */
public class User {

  private String name;

  /**
   * For JPA.
   */
  protected User() {}

  User(@NonNull Identifier id, @NonNull String name) {
    this.name = name;
  }

  /**
   * Creates a new builder.
   *
   * @return A new builder.
   */
  public static UserBuilder builder() {
    return UserBuilder.create();
  }

  @NotNull
  @Column(nullable = false, unique = true)
  public String getName() {
    return this.name;
  }

  void setName(@NonNull String name) {
    this.name = name;
  }

  /**
   * A user identifier.
   */
  public static class Identifier extends AbstractIdentitifier {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * For JPA.
     */
    protected Identifier() {}

    Identifier(@NonNull UUID id) {
      super(id);
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentitifier that) {
      return that instanceof Identifier;
    }
  }
}
