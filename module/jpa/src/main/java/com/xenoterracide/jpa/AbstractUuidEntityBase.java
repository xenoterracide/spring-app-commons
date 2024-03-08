// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * The type Abstract entity base.
 */
@MappedSuperclass
public abstract class AbstractUuidEntityBase implements Identifiable<UUID> {

  /**
   * The Identifier.
   */
  private UUID id = UuidCreator.getTimeOrderedEpoch();

  /**
   * Instantiates a new Abstract entity base.
   */
  protected AbstractUuidEntityBase() {}

  @Id
  @Column(name = "id", updatable = false, nullable = false)
  @Nullable
  @Override
  public UUID getId() {
    return id;
  }

  /**
   * Sets id. Called by JPA Provider
   *
   * @param id the id
   */
  void setId(@NonNull UUID id) {
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
  protected abstract boolean canEqual(@NonNull AbstractUuidEntityBase that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractUuidEntityBase that) {
      return that.canEqual(this) && Objects.equals(this.getId(), that.getId());
    }
    return false;
  }
}
