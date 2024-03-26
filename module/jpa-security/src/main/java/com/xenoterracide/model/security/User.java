// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.jpa.AbstractAggregate;
import com.xenoterracide.jpa.AbstractIdentitifier;
import com.xenoterracide.jpa.AbstractSurrogateEntity;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
@Table(name = "users")
public class User extends AbstractAggregate<User.@NonNull Identifier, @NonNull User> {

  private String name;
  private @NonNull Set<@NonNull IdentityProviderUser> identityProviderUsers = new HashSet<>();

  protected User() {}

  User(@NonNull Identifier id) {
    super(id);
  }

  @NotAudited
  @OneToMany(
    orphanRemoval = true,
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = IdentityProviderUser_.USER
  )
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

  public static class Identifier extends AbstractIdentitifier {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Identifier() {}

    protected Identifier(@NonNull UUID id) {
      super(id);
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentitifier that) {
      return that instanceof Identifier;
    }
  }
}
