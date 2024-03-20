// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.jpa.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Entity
@Audited
public class FooAggregate extends AbstractAggregate<FooAggregate.@NonNull Id> {

  private String name;

  private Set<@NonNull BarEntity> bars = new HashSet<>();

  protected FooAggregate() {}

  public FooAggregate(@NonNull Id id, @NonNull String name) {
    super(id);
    this.name = name;
  }

  static FooAggregate create(@NonNull String name) {
    return new FooAggregate(Id.create(), name);
  }

  public void addBar(@NonNull String name) {
    this.bars.add(BarEntity.create(this, name));
  }

  public void changeBarName(BarEntity.@NonNull Id id, @NonNull String name) {
    var bar =
      this.bars.stream()
        .filter(e -> e.getId().equals(id))
        .findFirst()
        .map(e -> {
          var b = new BarEntity(e.getId(), this, name);
          b.setVersion(e.getVersion());
          b.markDirty();
          return b;
        });

    this.bars.removeIf(e -> e.getId().equals(id));
    bar.ifPresent(this.bars::add);
  }

  @AuditMappedBy(mappedBy = "foo")
  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "foo")
  @NonNull
  Set<@NonNull BarEntity> getBars() {
    return this.bars;
  }

  @Initializer
  void setBars(@NonNull Set<@NonNull BarEntity> bars) {
    this.bars = bars;
  }

  @NotNull
  @Column(nullable = false)
  public @NonNull String getName() {
    return name;
  }

  @Initializer
  void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractEntity<?> that) {
    return that instanceof FooAggregate;
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