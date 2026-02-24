// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
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
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;

/**
 * A user.
 */
@Audited
@Entity
@AggregateRoot
@Table(name = "users")
public class User extends AbstractAggregate<User.UserId, User> implements Nameable {

  private String name;
  private Set<IdentityProviderUser> identityProviderUsers;

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
  User(UserId id, String name, Set<IdentityProviderUser> identityProviderUsers) {
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
  Set<IdentityProviderUser> getIdentityProviderUsers() {
    return this.identityProviderUsers;
  }

  @Initializer
  void setIdentityProviderUsers(Set<IdentityProviderUser> idpUsers) {
    this.identityProviderUsers = idpUsers;
  }

  /**
   * Gets an unmodifiable copy of the identity provider users.
   *
   * @return set of identity provider users
   */
  public Set<IdentityProviderUser> linkedIdentityProviderUsers() {
    return Set.copyOf(this.getIdentityProviderUsers());
  }

  /**
   * Links an identity provider to this user.
   *
   * @param issuer
   *   the identity provider issuer URL
   * @param subject
   *   the subject identifier from the identity provider
   * @param email
   *   the cached email address
   * @param emailVerified
   *   whether the email has been verified by the identity provider
   */
  public void linkIdentityProvider(URI issuer, String subject, String email, boolean emailVerified) {
    var idpUser = IdentityProviderUser.builder()
      .issuer(issuer)
      .subject(subject)
      .email(email)
      .emailVerified(emailVerified)
      .user(this)
      .build();
    this.getIdentityProviderUsers().add(idpUser);
  }

  @NotNull
  @Column(nullable = false, unique = true)
  @Override
  public String getName() {
    return this.name;
  }

  @Initializer
  void setName(String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(AbstractSurrogateEntity<?, ?> that) {
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

    UserId(UUID id) {
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
    protected boolean canEqual(AbstractIdentitifier that) {
      return that instanceof UserId;
    }
  }
}
