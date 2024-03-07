// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@MappedSuperclass
public abstract class AbstractEntityBase<ID extends Serializable> implements Identifiable<ID> {

  public AbstractEntityBase() {}

  @Id
  @Nullable
  @Override
  public ID getId() {
    return null;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.getId());
  }

  protected abstract boolean canEqual(@NonNull AbstractEntityBase<?> that);

  @Override
  public boolean equals(@Nullable Object other) {
    if (other instanceof AbstractEntityBase<?> that) {
      if (!(that.canEqual(this) && Objects.equals(this.getId(), that.getId()))) {
        return false;
      }
    }
    return false;
  }
}
