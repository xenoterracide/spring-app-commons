// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * The type Abstract uuid entity base.
 *
 * @param <ID> the type parameter
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends AbstractIdentity<? extends Serializable>>
  implements Identifiable<@NonNull ID> {

  private static final String NPE_MESSAGE = "use static factory method to create";

  /**
   * Surrogate Identifier.
   */
  private ID id;

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractEntity() {}

  /**
   * Instantiates a new Abstract uuid entity base.
   *
   * @param id the id
   */
  protected AbstractEntity(@NonNull ID id) {
    this.id = id;
  }

  @Id
  @NotNull
  @Override
  public @NonNull ID getId() {
    return this.id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   * @apiNote for JPA use only
   */
  @Initializer
  void setId(@NonNull ID id) {
    this.id = id;
  }

  @PrePersist
  void prePersist() {
    Objects.requireNonNull(this.id, NPE_MESSAGE);
    this.id.ensureId();
  }

  @PostLoad
  void postLoad() {
    Objects.requireNonNull(this.id, NPE_MESSAGE);
    this.id.ensureId();
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
   *   How to Write an Equality Method in Java
   *   </a>
   */
  protected abstract boolean canEqual(@NonNull AbstractEntity<?> that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractEntity<?> that) {
      return that.canEqual(this) && Objects.equals(this.id, that.id);
    }
    return false;
  }
}
