// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;
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

  private static final String NPE_MESSAGE = "use static factory method to create new id";

  /**
   * Surrogate Identifier.
   */
  private @Nullable ID id;
  private @NonNull Metadata metadata = new Metadata();

  /**
   * NO-OP parent constuctor for JPA only.
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
  @NotNull
  @Override
  @SuppressWarnings("NullAway") // shouldn't be null at runtime, makes validator error better
  public @NonNull ID getId() {
    return this.id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   * @apiNote for JPA use only
   */
  void setId(@NonNull ID id) {
    this.id = id;
  }

  @PrePersist
  void prePersist() {
    Objects.requireNonNull(this.id, NPE_MESSAGE);
    this.id.ensureId();
    this.metadata.onPersist();
  }

  @PostLoad
  void postLoad() {
    Objects.requireNonNull(this.id, NPE_MESSAGE);
    this.id.ensureId();
  }

  @PreUpdate
  void preUpdate() {
    this.metadata.onUpdate();
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
  protected abstract boolean canEqual(@NonNull AbstractUuidEntityBase<?> that);

  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractUuidEntityBase<?> that) {
      return that.canEqual(this) && Objects.equals(this.id, that.id);
    }
    return false;
  }

  /**
   * Gets metadata.
   *
   * @return the metadata
   */
  @Embedded
  public @NonNull Metadata getMetadata() {
    return metadata;
  }

  void setMetadata(@NonNull Metadata metadata) {
    this.metadata = metadata;
  }

  /**
   * The type Metadata.
   */
  @Embeddable
  public static class Metadata {

    private @Nullable ZonedDateTime created;

    private @Nullable ZonedDateTime modified;

    /**
     * NO-OP parent constuctor for JPA only.
     */
    protected Metadata() {}

    void onPersist() {
      var now = ZonedDateTime.now(ZoneId.systemDefault());
      this.created = now;
      this.modified = now;
    }

    void onUpdate() {
      this.modified = ZonedDateTime.now(ZoneId.systemDefault());
    }

    /**
     * Gets created.
     *
     * @return the created
     */
    @Column(updatable = false, nullable = false)
    public @Nullable ZonedDateTime getCreated() {
      return created;
    }

    /**
     * Sets created.
     *
     * @param created the created
     */
    void setCreated(@NonNull ZonedDateTime created) {
      this.created = created;
    }

    /**
     * Gets modified.
     *
     * @return the modified
     */
    @Column(updatable = false, nullable = false)
    public @Nullable ZonedDateTime getModified() {
      return modified;
    }

    /**
     * Sets modified.
     *
     * @param modified the modified
     */
    void setModified(@NonNull ZonedDateTime modified) {
      this.modified = modified;
    }
  }

  /**
   * Abstract domain identifier.
   */
  @MappedSuperclass
  public abstract static class AbstractIdentity implements Serializable {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    /**
     * The actual database UUID for id.
     */
    private @Nullable UUID id;

    /**
     * NO-OP parent constuctor for JPA only.
     */
    protected AbstractIdentity() {}

    /**
     * Instantiates a new Abstract identity.
     *
     * @param id the id
     */
    protected AbstractIdentity(@NonNull UUID id) {
      this.id = id;
    }

    /**
     * Ensure id.
     */
    protected void ensureId() {
      Objects.requireNonNull(this.id, NPE_MESSAGE);
    }

    /**
     * Gets id.
     *
     * @return the id
     * @apiNote for JPA use only
     */
    @NotNull
    @Column(name = "id", updatable = false, nullable = false, unique = true, columnDefinition = "uuid")
    @SuppressWarnings("NullAway") // shouldn't be null at runtime, makes validator error better
    public @NonNull UUID getId() {
      return this.id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @apiNote for JPA use only
     */
    void setId(@NonNull UUID id) {
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
     *   How to Write an Equality Method in Java
     *   </a>
     */
    protected abstract boolean canEqual(@NonNull AbstractIdentity that);

    @Override
    public final boolean equals(@Nullable Object other) {
      if (other instanceof AbstractIdentity that) {
        return that.canEqual(this) && Objects.equals(this.id, that.id);
      }
      return false;
    }

    @Override
    public final String toString() {
      return String.valueOf(this.id);
    }
  }
}
