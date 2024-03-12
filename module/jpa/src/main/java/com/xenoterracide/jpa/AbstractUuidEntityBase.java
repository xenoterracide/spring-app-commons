// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.jetbrains.annotations.VisibleForTesting;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * The type Abstract uuid entity base.
 *
 * @param <ID> the type parameter
 */
@MappedSuperclass
public abstract class AbstractUuidEntityBase<ID extends AbstractUuidEntityBase.AbstractIdentity>
  implements Identifiable<@NonNull ID> {

  /**
   * Surrogate Identifier.
   */
  private @NonNull ID id;

  /**
   * NO-OP Parent Constuctor.
   *
   * @apiNote for JPA use only
   */
  protected AbstractUuidEntityBase() {}

  /**
   * Instantiates a new Abstract uuid entity base.
   *
   * @param id the id
   */
  protected AbstractUuidEntityBase(@NonNull ID id) {
    this.id = id;
  }

  @Id
  @Override
  public @NonNull ID getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @apiNote for JPA use only
   * @param id the id
   */
  @Initializer
  void setId(@NonNull ID id) {
    this.id = id;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.getId());
  }

  /**
   * Can equal boolean.
   *
   * @param that the other object to compare to
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

  /**
   * The type Abstract uuid domain id.
   */
  @MappedSuperclass
  public abstract static class AbstractIdentity implements Serializable {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * The actual database UUID for id.
     */
    private UUID id;

    /**
     * NO-OP Parent Constuctor.
     *
     * @apiNote for JPA use only
     */
    @VisibleForTesting
    protected AbstractIdentity() {}

    protected AbstractIdentity(@NonNull UUID id) {
      this.id = id;
    }

    /**
     * Gets id.
     *
     * @apiNote for JPA use only
     * @return the id
     */
    @SuppressWarnings("dep-ann")
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "uuid")
    UUID getId() {
      return id;
    }

    /**
     * Sets id.
     *
     * @apiNote for JPA use only
     * @param id the id
     */
    @VisibleForTesting
    @Initializer
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
     * @param that the other object
     * @return the boolean
     */
    protected abstract boolean canEqual(@NonNull AbstractIdentity that);

    @Override
    public final boolean equals(@Nullable Object other) {
      if (other instanceof AbstractIdentity that) {
        return that.canEqual(this) && Objects.equals(this.getId(), that.getId());
      }
      return false;
    }

    @Override
    public final String toString() {
      return this.id.toString();
    }
  }
}
