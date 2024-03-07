// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

@Entity
public class TestEntity extends AbstractEntityBase<@NonNull UUID> {

  private @NonNull String name;

  protected TestEntity() {}

  public TestEntity(@NonNull UUID id, @NonNull String name) {
    super(id);
    this.name = name;
  }

  @NonNull
  @NotNull
  @Column(nullable = false)
  public String getName() {
    return name;
  }

  protected void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractEntityBase<?> that) {
    return that instanceof TestEntity;
  }
}
