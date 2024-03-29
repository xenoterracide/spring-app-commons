// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * An abstract class for Domain Entities with a database identifier.
 */
@MappedSuperclass
public abstract class AbstractIdentitifier implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The actual database UUID for id.
   */

  @NotNull
  private @Nullable UUID id;

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractIdentitifier() {}

  /**
   * Instantiates a new Abstract identity.
   *
   * @param id the id
   */
  protected AbstractIdentitifier(@NonNull UUID id) {
    this.id = id;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.id);
  }

  /**
   * That is an {@code instanceof} this concrete class.
   *
   * @param that the other object
   * @return the boolean
   * @see <a href="https://www.artima.com/articles/how-to-write-an-equality-method-in-java">
   * How to Write an Equality Method in Java
   * </a>
   */
  protected abstract boolean canEqual(@NonNull AbstractIdentitifier that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractIdentitifier that) {
      return that.canEqual(this) && Objects.equals(this.id, that.id);
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(this.id);
  }
}
