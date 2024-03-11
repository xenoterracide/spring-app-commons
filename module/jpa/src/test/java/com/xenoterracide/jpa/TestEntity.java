// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import org.jspecify.annotations.NonNull;

@Entity
public class TestEntity extends AbstractUuidEntityBase<TestEntity.@NonNull Id> {

  private @NonNull String name;

  public TestEntity(@NonNull String name) {
    super(new Id());
    this.name = name;
  }

  public TestEntity() {}

  @Override
  protected boolean canEqual(@NonNull AbstractUuidEntityBase<?> that) {
    return that instanceof TestEntity;
  }

  @NonNull
  @NotNull
  @Column(nullable = false)
  public String getName() {
    return name;
  }

  @Initializer
  void setName(@NonNull String name) {
    this.name = name;
  }

  public static class Id extends AbstractIdentity {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    @Override
    protected boolean canEqual(@NonNull AbstractIdentity that) {
      return that instanceof Id;
    }
  }
}