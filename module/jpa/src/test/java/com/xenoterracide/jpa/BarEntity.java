// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.jpa.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
public class BarEntity extends AbstractEntity<BarEntity.@NonNull Id> {

  private static final String[] INCLUDED_FIELDS_IN_TO_STRING = { "id", "name" };

  private @NonNull String name;

  private @NonNull FooAggregate foo;

  BarEntity(@NonNull Id id, @NonNull FooAggregate foo, @NonNull String name) {
    super(id);
    this.foo = foo;
    this.name = name;
  }

  public BarEntity() {}

  static @NonNull BarEntity create(@NonNull FooAggregate foo, @NonNull String name) {
    return new BarEntity(Id.create(), foo, name);
  }

  @ManyToOne(
    optional = false,
    cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
  )
  @JoinColumn(nullable = false, updatable = false, name = "foo_id")
  public @NonNull FooAggregate getFoo() {
    return foo;
  }

  @Initializer
  void setFoo(@NonNull FooAggregate foo) {
    this.foo = foo;
  }

  @Override
  protected boolean canEqual(@NonNull AbstractEntity<?> that) {
    return that instanceof BarEntity;
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
  protected String@NonNull[] includedFieldsInToString() {
    return INCLUDED_FIELDS_IN_TO_STRING;
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
