// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
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
import java.net.URI;
import java.util.Objects;
import org.jmolecules.ddd.annotation.Identity;
import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.Nullable;

/**
 * Identify the user when they log in using a 3rd party authentication provider.
 */
@jakarta.persistence.Entity
@org.jmolecules.ddd.annotation.Entity
@Table(name = "identity_provider_users")
public class IdentityProviderUser implements Entity<User, IdentityProviderUser.IdentityProviderUserId> {

  private IdentityProviderUserId id;
  private @Nullable User user;
  private String email;
  private boolean emailVerified;

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
  IdentityProviderUser(IdentityProviderUserId id) {
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
  public IdentityProviderUserId getId() {
    return this.id;
  }

  @Initializer
  void setId(IdentityProviderUserId id) {
    this.id = id;
  }

  /**
   * Get the identity provider issuer URL.
   *
   * @return the issuer URI
   */
  @NotNull
  @Transient
  public URI getIssuer() {
    return this.id.getIssuer();
  }

  /**
   * Get the subject identifier (unique within the identity provider).
   *
   * @return the subject identifier
   */
  @NotNull
  @Transient
  public String getSubject() {
    return this.id.getSubject();
  }

  /**
   * Get the cached email address.
   * This is for communication purposes only, not for identity.
   *
   * @return the email address
   */
  @NotNull
  @Column(nullable = false)
  public String getEmail() {
    return this.email;
  }

  @Initializer
  void setEmail(String email) {
    this.email = email;
  }

  /**
   * Check if the email has been verified by the identity provider.
   *
   * @return true if email is verified
   */
  @Column(nullable = false)
  public boolean isEmailVerified() {
    return this.emailVerified;
  }

  @Initializer
  void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
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
  public User getUser() {
    return Objects.requireNonNull(this.user);
  }

  @Initializer
  void setUser(User user) {
    this.user = user;
  }

  /**
   * Check if this instance could be equal to another object.
   *
   * @param that
   *   the object to compare
   * @return {@code true} if this instance could be equal to the other object
   */
  protected boolean canEqual(Entity<?, ?> that) {
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
   * The primary key for {@link IdentityProviderUser}.
   */
  @ValueObject
  @Embeddable
  public static class IdentityProviderUserId implements Serializable, Identifier {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The identity provider issuer URL.
     */
    private URI issuer;

    /**
     * The subject identifier (unique within the identity provider).
     */
    private String subject;

    /**
     * The user.
     */

    private User.UserId userId;

    /**
     * For JPA.
     */
    protected IdentityProviderUserId() {}

    IdentityProviderUserId(URI issuer, String subject, User.UserId userId) {
      this.issuer = issuer;
      this.subject = subject;
      this.userId = userId;
    }

    /**
     * Get the identity provider issuer URL.
     *
     * @return the issuer URI
     */
    @Column(nullable = false, updatable = false, name = "issuer")
    URI getIssuer() {
      return this.issuer;
    }

    @Initializer
    void setIssuer(URI issuer) {
      this.issuer = issuer;
    }

    /**
     * Get the subject identifier.
     *
     * @return the subject identifier
     */
    @Column(nullable = false, updatable = false, name = "subject")
    String getSubject() {
      return this.subject;
    }

    @Initializer
    void setSubject(String subject) {
      this.subject = subject;
    }

    User.UserId getUserId() {
      return this.userId;
    }

    @Initializer
    void setUserId(User.UserId userId) {
      this.userId = userId;
    }

    /**
     * Check if this instance could be equal to another object.
     *
     * @param that
     *   the object to compare
     * @return {@code true} if this instance could be equal to the other object.
     */
    protected boolean canEqual(Serializable that) {
      return that instanceof IdentityProviderUserId;
    }

    @Override
    public final boolean equals(Object o) {
      if (o instanceof IdentityProviderUserId that) {
        // CHECKSTYLE.OFF: UnnecessaryParentheses
        return (
          that.canEqual(this) &&
          Objects.equals(this.issuer, that.issuer) &&
          Objects.equals(this.subject, that.subject) &&
          Objects.equals(this.userId, that.userId)
        );
        // CHECKSTYLE.ON: UnnecessaryParentheses
      }
      return false;
    }

    @Override
    public final int hashCode() {
      return Objects.hash(this.issuer, this.subject, this.userId);
    }
  }
}
