// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Entity
public class TestAggregate extends AbstractUuidEntityBase<TestAggregate.@NonNull Id> {

  @Column(nullable = false)
  private @Nullable String name;

  protected TestAggregate() {}

  public TestAggregate(@NonNull Id id, @NonNull String name) {
    super(id);
    this.name = name;
  }

  @NotNull
  @SuppressWarnings("MultipleNullnessAnnotations")
  public @Nullable String getName() {
    return name;
  }

  void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractUuidEntityBase<?> that) {
    return that instanceof TestAggregate;
  }

  public static class Id extends AbstractIdentity {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    protected Id() {}

    private Id(@NonNull UUID id) {
      super(id);
    }

    public static Id create() {
      return new Id(UuidCreator.getTimeOrderedEpoch());
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentity that) {
      return that instanceof Id;
    }
  }
}
