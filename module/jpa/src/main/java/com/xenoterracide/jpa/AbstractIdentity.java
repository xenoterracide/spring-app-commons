// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * An abstract class for Domain Entities with a database identifier.
 *
 * @param <ID> the concrete database identifier type
 */
@MappedSuperclass
public abstract class AbstractIdentity<ID extends Serializable> implements Serializable {

  private static final String NPE_MESSAGE = "use static factory method to create";

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * The actual database UUID for id.
   */
  private @Nullable ID id;

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractIdentity() {}

  /**
   * Instantiates a new Abstract identity.
   *
   * @param id the id
   */
  protected AbstractIdentity(@NonNull ID id) {
    this.id = Objects.requireNonNull(id);
  }

  /**
   * Ensure id.
   */
  final void ensureId() {
    Objects.requireNonNull(this.id, AbstractIdentity.NPE_MESSAGE);
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
  protected abstract boolean canEqual(@NonNull AbstractIdentity<?> that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractIdentity<?> that) {
      return that.canEqual(this) && Objects.equals(this.id, that.id);
    }
    return false;
  }

  @Override
  public String toString() {
    return String.valueOf(this.id);
  }
}
