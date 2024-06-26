// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.jpa.AbstractAggregate;
import com.xenoterracide.jpa.AbstractIdentitifier;
import com.xenoterracide.jpa.AbstractSurrogateEntity;
import com.xenoterracide.model.Nameable;
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
import java.util.stream.Collectors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.jspecify.annotations.NonNull;

/**
 * A user.
 */
@Audited
@Entity
@Table(name = "users")
public class User extends AbstractAggregate<User.@NonNull Identifier, @NonNull User> implements Nameable {

  private String name;
  private @NonNull Set<@NonNull IdentityProviderUser> identityProviderUsers;

  /**
   * For JPA.
   */
  protected User() {}

  User(
    @NonNull Identifier id,
    @NonNull String name,
    @NonNull Set<@NonNull IdentityProviderUserBuilder> identityProviderUsers
  ) {
    super(id);
    this.name = name;
    this.identityProviderUsers = identityProviderUsers
      .stream()
      .map(b -> b.user(this))
      .map(b -> b.build())
      .collect(Collectors.toSet());
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
  protected boolean canEqual(@NonNull AbstractSurrogateEntity<?> that) {
    return that instanceof User;
  }

  /**
   * A user identifier.
   */
  public static class Identifier extends AbstractIdentitifier {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * For JPA.
     */
    protected Identifier() {}

    Identifier(@NonNull UUID id) {
      super(id);
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentitifier that) {
      return that instanceof Identifier;
    }
  }
}
