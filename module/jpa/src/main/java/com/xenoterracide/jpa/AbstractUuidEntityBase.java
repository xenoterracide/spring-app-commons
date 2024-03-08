// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * The type Abstract entity base.
 */
@MappedSuperclass
public abstract class AbstractUuidEntityBase<T extends AbstractUuidDomainId> implements Identifiable<@NonNull T> {

  private @NonNull T id;

  protected AbstractUuidEntityBase() {}

  /**
   * Instantiates a new Abstract entity base.
   */
  protected AbstractUuidEntityBase(@NonNull T id) {
    this.id = id;
  }

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @Override
  public @NonNull T getId() {
    return id;
  }

  /**
   * Sets id. Called by JPA Provider
   *
   * @param id the id
   */

  @Initializer
  void setId(@NonNull T id) {
    this.id = id;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.getId());
  }

  /**
   * Can equal boolean.
   *
   * @param that the that
   * @return the boolean
   */
  protected abstract boolean canEqual(@NonNull AbstractUuidEntityBase<?> that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractUuidEntityBase<?> that) {
      return that.canEqual(this) && Objects.equals(this.getId(), that.getId());
    }
    return false;
  }
}
