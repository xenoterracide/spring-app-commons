// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Identify the user when they log in using a 3rd party authentication provider.
 */
@jakarta.persistence.Entity
@org.jmolecules.ddd.annotation.Entity
@Table(name = "identity_provider_users")
public class IdentityProviderUser implements Entity<User, IdentityProviderUser.@NonNull IdentityProviderUserId> {

  private @NonNull IdentityProviderUserId id;
  private @Nullable User user;

  /**
   * For JPA.
   */
  protected IdentityProviderUser() {}

  /**
   * Create a new instance.
   *
   * @param id
   *   the primary key
   */
  IdentityProviderUser(@NonNull IdentityProviderUserId id) {
    this.id = id;
  }

  /**
   * Create a new builder.
   *
   * @return the builder
   */
  public static IdentityProviderUserBuilder builder() {
    return IdentityProviderUserBuilder.create();
  }

  boolean hasUser() {
    return this.user != null;
  }

  @Identity
  @EmbeddedId
  @NotNull
  @Override
  public @NonNull IdentityProviderUserId getId() {
    return this.id;
  }

  @Initializer
  void setId(@NonNull IdentityProviderUserId id) {
    this.id = id;
  }

  /**
   * Get the identity provider.
   *
   * @return the identity provider
   */
  @NotNull
  @Transient
  public @NonNull IdP getIdP() {
    return this.id.getIdP();
  }

  /**
   * Get the identity provider user id.
   *
   * @return the identity provider user id
   */
  @NotNull
  @Transient
  public @NonNull String getIdPUserId() {
    return this.id.getIdPUserId();
  }

  /**
   * Get the user.
   *
   * @return the user
   * @implNote this method can throw {@link NullPointerException} if this object is not properly initialized. Please
   *   look along initialization paths for the real issue.
   */
  @MapsId("userId")
  @NotNull
  @ManyToOne(
    optional = false,
    fetch = FetchType.LAZY,
    cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
  )
  @JoinColumn(
    nullable = false,
    updatable = false,
    name = "user_id",
    foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
  )
  public @NonNull User getUser() {
    return Objects.requireNonNull(this.user);
  }

  @Initializer
  void setUser(@NonNull User user) {
    this.user = user;
  }

  /**
   * Check if this instance could be equal to another object.
   *
   * @param that
   *   the object to compare
   * @return {@code true} if this instance could be equal to the other object
   */
  protected boolean canEqual(@NonNull Entity<?, ?> that) {
    return that instanceof IdentityProviderUser;
  }

  @Override
  public final boolean equals(Object o) {
    if (o instanceof IdentityProviderUser that) {
      return that.canEqual(this) && Objects.equals(this.id, that.id);
    }
    return false;
  }

  @Override
  public final int hashCode() {
    return Objects.hashCode(this.id);
  }

  /**
   * The identity provider.
   */
  public enum IdP {
    /**
     * Auth0.
     */
    AUTH0,
  }

  /**
   * The primary key for {@link IdentityProviderUser}.
   */
  @ValueObject
  @Embeddable
  public static class IdentityProviderUserId implements Serializable, Identifier {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The identity provider.
     */
    private IdP idP;

    /**
     * The identity provider user id.
     */
    private String idPUserId;

    /**
     * The user.
     */

    private User.UserId userId;

    /**
     * For JPA.
     */
    protected IdentityProviderUserId() {}

    IdentityProviderUserId(@NonNull IdP idP, @NonNull String idPUserId, User.@NonNull UserId userId) {
      this.idP = idP;
      this.idPUserId = idPUserId;
      this.userId = userId;
    }

    @Column(nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    IdP getIdP() {
      return this.idP;
    }

    @Initializer
    void setIdP(@NonNull IdP idP) {
      this.idP = idP;
    }

    /**
     * Check if this instance could be equal to another object.
     *
     * @param that
     *   the object to compare
     * @return {@code true} if this instance could be equal to the other object.
     */
    protected boolean canEqual(@NonNull Serializable that) {
      return that instanceof IdentityProviderUserId;
    }

    @Override
    public final boolean equals(Object o) {
      if (o instanceof IdentityProviderUserId that) {
        // CHECKSTYLE.OFF: UnnecessaryParentheses
        return (
          that.canEqual(this) &&
          this.idP == that.idP &&
          Objects.equals(this.idPUserId, that.idPUserId) &&
          Objects.equals(this.userId, that.userId)
        );
        // CHECKSTYLE.ON: UnnecessaryParentheses
      }
      return false;
    }

    @Override
    public final int hashCode() {
      return Objects.hash(this.idP, this.idPUserId, this.userId);
    }

    @Column(nullable = false, updatable = false, name = "idp_user_id")
    String getIdPUserId() {
      return this.idPUserId;
    }

    @Initializer
    void setIdPUserId(@NonNull String idPUserId) {
      this.idPUserId = idPUserId;
    }

    User.UserId getUserId() {
      return this.userId;
    }

    @Initializer
    void setUserId(User.@NonNull UserId userId) {
      this.userId = userId;
    }
  }
}
