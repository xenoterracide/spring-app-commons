// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
  private static final String[] INCLUDED_FIELDS_IN_TO_STRING = { "id" };

  @Transient
  private boolean dirty;

  /**
   * Surrogate Identifier.
   */
  private @NonNull ID id;
  private @Nullable Integer version;

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractEntity() {
    this.dirty = false;
  }

  /**
   * Instantiates a new Abstract uuid entity base.
   *
   * @param id the id
   */
  protected AbstractEntity(@NonNull ID id) {
    this.id = id;
  }

  @Version
  @Nullable
  @Column(nullable = false)
  Integer getVersion() {
    return this.version;
  }

  void setVersion(Integer version) {
    this.version = version;
  }

  /**
   * Mark this entity as having changed in memory from persistence.
   */
  protected void markDirty() {
    this.dirty = true;
  }

  @Id
  @NotNull
  @Column(nullable = false, updatable = false, unique = true)
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
    return Objects.hash(this.id, this.version, this.dirty);
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
      // CHECKSTYLE.OFF: UnnecessaryParentheses
      return (
        that.canEqual(this) &&
        Objects.equals(this.id, that.id) &&
        Objects.equals(this.version, that.version) &&
        this.dirty == that.dirty
      );
      // CHECKSTYLE.ON: UnnecessaryParentheses
    }
    return false;
  }

  /**
   * Override to change the fields included in {@link #toString()}.
   *
   * @implSpec the fields should be a static final array of strings
   *
   * @return the fields included in {@link #toString()}
   */
  protected @NonNull String[] includedFieldsInToString() {
    return INCLUDED_FIELDS_IN_TO_STRING;
  }

  @Override
  public final @NonNull String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
      .setIncludeFieldNames(this.includedFieldsInToString())
      .toString();
  }
}
