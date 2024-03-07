// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * The type Abstract entity base.
 *
 * @param <ID> the type parameter
 */
@MappedSuperclass
public abstract class AbstractEntityBase<ID extends Serializable> implements Identifiable<ID> {

  /**
   * The Identifier.
   */
  private @Nullable ID id;

  /**
   * Instantiates a new Abstract entity base.
   */
  protected AbstractEntityBase() {}

  /**
   * Instantiates a new Abstract entity base.
   *
   * @param id the id
   */
  protected AbstractEntityBase(@NonNull ID id) {
    this.id = id;
  }

  @Id
  @Nullable
  @Override
  public ID getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  protected void setId(ID id) {
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
  protected abstract boolean canEqual(@NonNull AbstractEntityBase<?> that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractEntityBase<?> that) {
      return that.canEqual(this) && Objects.equals(this.getId(), that.getId());
    }
    return false;
  }
}
