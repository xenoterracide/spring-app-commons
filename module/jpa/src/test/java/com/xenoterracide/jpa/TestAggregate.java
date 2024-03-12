// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.io.Serial;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

@Entity
public class TestAggregate extends AbstractUuidEntityBase<TestAggregate.@NonNull Id> {

  protected TestAggregate() {}

  public TestAggregate(@NonNull Id id) {
    super(id);
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

    @Override
    protected boolean canEqual(@NonNull AbstractIdentity that) {
      return that instanceof Id;
    }

    public static Id create() {
      return new Id(UuidCreator.getTimeOrderedEpoch());
    }
  }
}
