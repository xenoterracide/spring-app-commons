// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.io.Serial;
import org.jspecify.annotations.NonNull;

@Entity
public class TestAggregate extends AbstractUuidEntityBase<TestAggregate.@NonNull Id> {

  public TestAggregate() {
    super(new Id());
  }

  @Override
  protected boolean canEqual(@NonNull AbstractUuidEntityBase<?> that) {
    return that instanceof TestAggregate;
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
