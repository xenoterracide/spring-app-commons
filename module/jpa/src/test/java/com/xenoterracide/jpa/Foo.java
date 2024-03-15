// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Entity
@Audited
public class Foo extends AbstractAggregate<Foo.@NonNull Id> {

  @Column(nullable = false)
  private String name;

  private Set<@NonNull Bar> bars = new HashSet<>();

  protected Foo() {}

  public Foo(@NonNull Id id, @NonNull String name) {
    super(id);
    this.name = name;
  }

  @JoinColumn(nullable = false, name = "foo_id")
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  public @NonNull Set<@NonNull Bar> getBars() {
    return bars;
  }

  @Initializer
  void setBars(@NonNull Set<@NonNull Bar> tags) {
    this.bars = tags;
  }

  @NotNull
  public @NonNull String getName() {
    return name;
  }

  @Initializer
  void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractEntity<?> that) {
    return that instanceof Foo;
  }

  public static class Id extends AbstractIdentity<@NonNull UUID> {

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
    protected boolean canEqual(@NonNull AbstractIdentity<?> that) {
      return that instanceof Id;
    }
  }
}
