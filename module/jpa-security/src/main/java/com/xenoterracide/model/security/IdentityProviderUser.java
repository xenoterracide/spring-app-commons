// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
import org.jspecify.annotations.NonNull;

/**
 * Represents a user from an identity provider.
 */
@Entity
@Table(name = "identity_provider_users")
public class IdentityProviderUser implements Identifiable<IdentityProviderUser.@NonNull Identifier> {

  private IdentityProviderUser.@NonNull Identifier id;
  private @NonNull User user;

  /**
   * For JPA.
   */
  protected IdentityProviderUser() {}

  /**
   * Create a new instance.
   *
   * @param user the user
   * @param idP the identity provider
   * @param idPUserId the identity provider user id
   */
  protected IdentityProviderUser(@NonNull User user, @NonNull IdP idP, @NonNull String idPUserId) {
    this.id = new Identifier(idP, idPUserId, user.getId());
    this.user = user;
  }

  @EmbeddedId
  @NotNull
  @Override
  public IdentityProviderUser.@NonNull Identifier getId() {
    return this.id;
  }

  @Initializer
  void setId(IdentityProviderUser.@NonNull Identifier id) {
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
    return this.user;
  }

  @Initializer
  void setUser(@NonNull User user) {
    this.user = user;
  }

  protected boolean canEqual(@NonNull Identifiable<?> that) {
    return that instanceof IdentityProviderUser;
  }

  /**
   * The identity provider.
   */
  public enum IdP {
    AUTH0,
  }

  /**
   * The primary key for {@link IdentityProviderUser}.
   */
  @Embeddable
  public static class Identifier implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private IdP idP;
    private String idPUserId;

    private User.Identifier userId;

    /**
     * For JPA.
     */
    protected Identifier() {}

    Identifier(@NonNull IdP idP, @NonNull String idPUserId, User.@NonNull Identifier userId) {
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

    protected boolean canEqual(@NonNull Serializable that) {
      return that instanceof Identifier;
    }

    @Override
    public final boolean equals(Object o) {
      if (o instanceof Identifier that) {
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

    User.Identifier getUserId() {
      return this.userId;
    }

    @Initializer
    void setUserId(User.@NonNull Identifier userId) {
      this.userId = userId;
    }
  }
}
