// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.commons.jpa.AbstractAggregate;
import com.xenoterracide.commons.jpa.AbstractIdentitifier;
import com.xenoterracide.commons.jpa.AbstractSurrogateEntity;
import com.xenoterracide.commons.model.Nameable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Set;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;
import org.jspecify.annotations.NonNull;

/**
 * A user.
 */
@Audited
@Entity
@AggregateRoot
@Table(name = "users")
public class User extends AbstractAggregate<User.@NonNull UserId, @NonNull User> implements Nameable {

  private @NonNull String name;
  private @NonNull Set<@NonNull IdentityProviderUser> identityProviderUsers;

  /**
   * For JPA.
   */
  protected User() {}

  /**
   * use {@link UserBuilder#create()} instead of this directly.
   *
   * @param id
   *   identity
   * @param name
   *   username
   * @param identityProviderUsers
   *   the linked identity provider users
   */
  User(@NonNull UserId id, @NonNull String name, @NonNull Set<@NonNull IdentityProviderUser> identityProviderUsers) {
    super(id);
    this.name = name;
    this.identityProviderUsers = identityProviderUsers;
  }

  /**
   * Creates a new builder.
   *
   * @return A new builder.
   */
  public static UserBuilder builder() {
    return UserBuilder.create();
  }

  @NotAudited
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  @NonNull
  Set<@NonNull IdentityProviderUser> getIdentityProviderUsers() {
    return this.identityProviderUsers;
  }

  @Initializer
  void setIdentityProviderUsers(@NonNull Set<@NonNull IdentityProviderUser> idpUsers) {
    this.identityProviderUsers = idpUsers;
  }

  /**
   * Gets an unmodifiable copy of the identity provider users.
   *
   * @return set of identity provider users
   */
  @NonNull
  public Set<@NonNull IdentityProviderUser> linkedIdentityProviderUsers() {
    return Set.copyOf(this.getIdentityProviderUsers());
  }

  /**
   * Links an identity provider to this user.
   *
   * @param idp
   *   identity provider
   * @param idpUserId
   *   the user identifier we got for that identity provider
   */
  public void linkIdentityProvider(IdentityProviderUser.@NonNull IdP idp, @NonNull String idpUserId) {
    var idpUser = IdentityProviderUser.builder().idP(idp).idPUserId(idpUserId).user(this).build();
    this.getIdentityProviderUsers().add(idpUser);
  }

  @NotNull
  @Column(nullable = false, unique = true)
  @Override
  public String getName() {
    return this.name;
  }

  @Initializer
  void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractSurrogateEntity<?, ?> that) {
    return that instanceof User;
  }

  /**
   * A user identifier.
   */
  @ValueObject
  public static class UserId extends AbstractIdentitifier {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * For JPA.
     */
    protected UserId() {}

    UserId(@NonNull UUID id) {
      super(id);
    }

    /**
     * Creates a new identifier.
     *
     * @return A new identifier.
     */
    public static UserId create() {
      return new UserId(UuidCreator.getTimeOrderedEpoch());
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentitifier that) {
      return that instanceof UserId;
    }
  }
}
