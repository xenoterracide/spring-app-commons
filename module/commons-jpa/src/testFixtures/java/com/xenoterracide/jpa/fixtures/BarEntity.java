// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.jpa.fixtures;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.commons.jpa.AbstractIdentitifier;
import com.xenoterracide.commons.jpa.AbstractSurrogateEntity;
import com.xenoterracide.commons.model.EntityIdentifier;
import com.xenoterracide.tools.java.annotation.Initializer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.UUID;
import org.hibernate.envers.Audited;
import org.jspecify.annotations.NonNull;

@Audited
@Entity
public class BarEntity extends AbstractSurrogateEntity<BarEntity.@NonNull Id, @NonNull FooAggregate> {

  private static final String[] INCLUDED_FIELDS_IN_TO_STRING = { "id", "name" };

  private String name;

  private FooAggregate foo;

  BarEntity(Id id, FooAggregate foo, String name) {
    super(id);
    this.foo = foo;
    this.name = name;
  }

  public BarEntity() {}

  public static BarEntity create(FooAggregate foo, String name) {
    return new BarEntity(BarEntity.Id.create(), foo, name);
  }

  @ManyToOne(
    optional = false,
    fetch = FetchType.LAZY,
    cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
  )
  @JoinColumn(nullable = false, updatable = false, name = "foo_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
  public FooAggregate getFoo() {
    return this.foo;
  }

  @Initializer
  void setFoo(FooAggregate foo) {
    this.foo = foo;
  }

  @Override
  protected boolean canEqual(AbstractSurrogateEntity<?, ?> that) {
    return that instanceof BarEntity;
  }

  @NotNull
  @Column(nullable = false)
  public String getName() {
    return this.name;
  }

  @Initializer
  void setName(String name) {
    this.name = name;
  }

  void changeName(String name) {
    this.setName(name);
    this.markDirty();
    this.foo.registerEvent(new NameChanged(this.getId(), name));
  }

  @Override
  protected String[] includedFieldsInToString() {
    return INCLUDED_FIELDS_IN_TO_STRING;
  }

  public static class Id extends AbstractIdentitifier {

    @Serial
    private static final long serialVersionUID = 1L;

    protected Id() {}

    private Id(UUID id) {
      super(id);
    }

    public static Id create() {
      return new Id(UuidCreator.getTimeOrderedEpoch());
    }

    @Override
    protected boolean canEqual(@NonNull AbstractIdentitifier that) {
      return that instanceof Id;
    }
  }

  public record NameChanged(BarEntity.Id id, String name, Class<BarEntity> type) implements
    EntityIdentifier<@NonNull Id, BarEntity> {
    public NameChanged(BarEntity.Id id, String name) {
      this(id, name, BarEntity.class);
    }
  }
}
