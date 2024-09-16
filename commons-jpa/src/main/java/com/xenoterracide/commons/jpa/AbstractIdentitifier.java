// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.commons.jpa;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * An abstract class for Domain Entities with a database identifier.
 */
@ValueObject
@MappedSuperclass
public abstract class AbstractIdentitifier implements Serializable, Identifier {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The actual database UUID for id.
   */

  @NotNull
  private @Nullable UUID value;

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractIdentitifier() {}

  /**
   * Instantiates a new Abstract identity.
   *
   * @param value
   *   the id
   */
  protected AbstractIdentitifier(@NonNull UUID value) {
    this.value = value;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.value);
  }

  /**
   * That is an {@code instanceof} this concrete class.
   *
   * @param that
   *   the other object
   * @return the boolean
   * @see <a href="https://www.artima.com/articles/how-to-write-an-equality-method-in-java">
   *   How to Write an Equality Method in Java
   *   </a>
   */
  protected abstract boolean canEqual(@NonNull AbstractIdentitifier that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractIdentitifier that) {
      return that.canEqual(this) && Objects.equals(this.value, that.value);
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(this.value);
  }
}
