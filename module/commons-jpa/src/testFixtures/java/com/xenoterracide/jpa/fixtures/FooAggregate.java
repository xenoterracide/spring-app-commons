// SPDX-FileCopyrightText: Copyright © 2024 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.jpa.fixtures;

import static java.util.function.Predicate.isEqual;

import com.github.f4b6a3.uuid.UuidCreator;
import com.xenoterracide.commons.jpa.AbstractAggregate;
import com.xenoterracide.commons.jpa.AbstractIdentitifier;
import com.xenoterracide.commons.jpa.AbstractSurrogateEntity;
import com.xenoterracide.commons.jpa.DomainEvent;
import com.xenoterracide.commons.model.EntityIdentifier;
import com.xenoterracide.commons.model.Identifiable;
import com.xenoterracide.tools.java.annotation.Initializer;
import com.xenoterracide.tools.java.function.PredicateTools;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class FooAggregate extends AbstractAggregate<FooAggregate.Id, FooAggregate> {

  private String name;

  private Set<BarEntity> bars = new HashSet<>();

  public FooAggregate() {}

  public FooAggregate(Id id, String name) {
    super(id);
    this.name = name;
  }

  public static FooAggregate create(String name) {
    return new FooAggregate(Id.create(), name);
  }

  protected void registerEvent(EntityIdentifier<BarEntity.Id, BarEntity> event) {
    super.registerEvent(FooEvent.create(this.getId(), event));
  }

  /**
   * override for testing
   *
   * @return domain events
   */
  @Override
  protected Collection<DomainEvent<?, Id, FooAggregate, ?>> domainEvents() {
    return super.domainEvents();
  }

  public BarEntity addBar(String name) {
    var bar = BarEntity.create(this, name);
    this.bars.add(bar);
    return bar;
  }

  public void changeBarName(BarEntity.Id id, String name) {
    this.bars.stream()
      .filter(PredicateTools.is(Identifiable::getId, isEqual(id)))
      .findAny()
      .ifPresent(e -> e.changeName(name));
  }

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "foo")
  public Set<BarEntity> getBars() {
    return this.bars;
  }

  void setBars(Set<BarEntity> bars) {
    this.bars = bars;
  }

  @NotNull
  @Column(nullable = false)
  public String getName() {
    return this.name;
  }

  @Initializer
  public void setName(String name) {
    this.name = name;
  }

  @Override
  protected boolean canEqual(AbstractSurrogateEntity<?, ?> that) {
    return that instanceof FooAggregate;
  }

  public static class Id extends AbstractIdentitifier {

    @Serial
    private static final long serialVersionUID = 1L;

    public Id() {}

    private Id(UUID id) {
      super(id);
    }

    public static Id create() {
      return new Id(UuidCreator.getTimeOrderedEpoch());
    }

    @Override
    protected boolean canEqual(AbstractIdentitifier that) {
      return that instanceof Id;
    }
  }
}
