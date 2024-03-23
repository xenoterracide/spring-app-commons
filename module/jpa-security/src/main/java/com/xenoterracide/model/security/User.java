// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security;

import com.xenoterracide.jpa.AbstractAggregate;
import com.xenoterracide.jpa.AbstractEntity;
import com.xenoterracide.jpa.AbstractIdentity;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

@Entity
public class User extends AbstractAggregate<User.@NonNull Id, @NonNull User> {

  private String name;

  protected User() {}

  User(@NonNull Id id) {
    super(id);
  }

  @NotNull
  @Column(nullable = false, unique = true)
  public String getName() {
    return this.name;
  }

  @Initializer
  void setName(String name) {
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
