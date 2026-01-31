// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa;

import com.xenoterracide.commons.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Base class for entities that use a surrogate identifier.
 *
 * @param <ID>
 *   the type parameter
 * @param <AGG>
 *   the type parameter
 */
@MappedSuperclass
public abstract class AbstractSurrogateEntity<ID extends Identifier & Serializable, AGG extends AggregateRoot<AGG, ?>>
  implements Entity<AGG, ID>, Identifiable<@NonNull ID> {

  private @Nullable ID id;

  /**
   * @implNote The optimistic lock is {@link Nullable} because otherwise a new object would have the same version as
   *   the first persisted version causing you to be able to have a safe upgrade instead of a conflict.
   */
  private @Nullable Integer version;

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractSurrogateEntity() {}

  /**
   * Instantiates a new Abstract uuid entity base.
   *
   * @param id
   *   the id
   */
  protected AbstractSurrogateEntity(ID id) {
    this.id = id;
  }

  @Version
  @Column(nullable = false)
  protected @Nullable Integer getVersion() {
    return this.version;
  }

  protected void setVersion(Integer version) {
    this.version = version;
  }

  @Id
  @Valid
  @NotNull
  @Identity
  @Column(nullable = false, updatable = false, unique = true)
  @Override
  public @NonNull ID getId() {
    return Objects.requireNonNull(this.id, "id");
  }

  /**
   * Sets id.
   *
   * @param id
   *   the id
   * @apiNote for JPA use only
   */
  @Initializer
  protected void setId(ID id) {
    this.id = Objects.requireNonNull(id);
  }

  @Override
  public ID id() {
    return this.getId();
  }

  @Override
  public final int hashCode() {
    return Objects.hash(this.id, this.version);
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
  protected abstract boolean canEqual(AbstractSurrogateEntity<?, ?> that);

  /**
   * @param other
   *   the reference object with which to compare.
   * @return {@code true} if this object is the same as the {@code other}
   * @implNote check version to avoid bad behavior when added to a {@link java.util.Set}. In java when two objects
   *   are equal if you try to add bot to the {@link java.util.Set}, the one being added second is ignored. When
   *   interacting with JPA this can cause surprising behavior as you might wonder why your update isn't updating at
   *   all. Instead, ensuring that the same object is added to the {@link java.util.Set} twice will ensure a runtime
   *   error that can be easily seen.
   */
  @Override
  public final boolean equals(@Nullable Object other) {
    if (other instanceof AbstractSurrogateEntity<?, ?> that) {
      // CHECKSTYLE.OFF: UnnecessaryParentheses
      return (that.canEqual(this) && Objects.equals(this.id, that.id) && Objects.equals(this.version, that.version));
      // CHECKSTYLE.ON: UnnecessaryParentheses
    }
    return false;
  }

  @Override
  public final String toString() {
    return new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
      .setIncludeFieldNames(AbstractSurrogateEntity_.ID, AbstractSurrogateEntity_.VERSION)
      .toString();
  }
}
