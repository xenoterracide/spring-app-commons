// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.jpa.AbstractAggregate;
import com.xenoterracide.jpa.AbstractEntity;
import com.xenoterracide.jpa.AbstractIdentity;
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
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
@Table(name = "users")
public class User extends AbstractAggregate<User.@NonNull Id, @NonNull User> {

  private String name;
  private @NonNull Set<@NonNull ForeignIdPUserIdentity> foreignIdPUserIdentities = new HashSet<>();

  protected User() {}

  User(@NonNull Id id) {
    super(id);
  }

  @AuditMappedBy(mappedBy = ForeignIdPUserIdentity_.USER)
  @OneToMany(
    orphanRemoval = true,
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = ForeignIdPUserIdentity_.USER
  )
  Set<ForeignIdPUserIdentity> getForeignIdPUserIdentities() {
    return this.foreignIdPUserIdentities;
  }

  @Initializer
  void setForeignIdPUserIdentities(@NonNull Set<@NonNull ForeignIdPUserIdentity> foreignIdPUserIdentities) {
    this.foreignIdPUserIdentities = foreignIdPUserIdentities;
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
  protected boolean canEqual(@NonNull AbstractEntity<?> that) {
    return that instanceof User;
  }

  public static class Id extends AbstractIdentity {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Id() {}

    protected Id(@NonNull UUID id) {
      super(id);
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentity that) {
      return that instanceof Id;
    }
  }
}
