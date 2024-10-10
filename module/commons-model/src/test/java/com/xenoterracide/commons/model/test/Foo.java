// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.commons.model.test;

import com.xenoterracide.commons.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

@Entity
class Foo implements Identifiable<Foo.@NonNull FooId> {

  @Id
  private FooId id;

  @Override
  public FooId getId() {
    return id;
  }

  @Initializer
  void setId(@NonNull FooId id) {
    this.id = id;
  }

  static class FooId implements Serializable, Identifier {

    @Serial
    private static final long serialVersionUID = 1L;

    @GeneratedValue
    private Long value;

    FooId(@NonNull Long value) {
      this.value = value;
    }

    protected FooId() {}

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof FooId fooId)) return false;
      return Objects.equals(value, fooId.value);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(value);
    }

    @Override
    public String toString() {
      return String.valueOf(this.value);
    }
  }
}
