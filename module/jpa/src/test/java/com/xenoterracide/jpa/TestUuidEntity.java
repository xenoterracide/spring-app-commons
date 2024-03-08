// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NonNull;

@Entity
public class TestUuidEntity extends AbstractUuidEntityBase {

  private String name;

  protected TestUuidEntity() {}

  public TestUuidEntity(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractUuidEntityBase that) {
    return that instanceof TestUuidEntity;
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
}
