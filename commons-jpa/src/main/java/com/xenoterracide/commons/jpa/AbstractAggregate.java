// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.commons.jpa;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

/**
 * An abstract class for Domain Aggregates.
 *
 * @param <ID>
 *   type parameter
 * @param <THIS>
 *   type parameter
 */
@MappedSuperclass
public abstract class AbstractAggregate<ID extends Identifier & Serializable, THIS extends AggregateRoot<THIS, ID>>
  extends AbstractSurrogateEntity<ID, THIS>
  implements AggregateRoot<THIS, ID> {

  private final @Transient List<DomainEvent<?, ID, THIS, ?>> domainEvents = new ArrayList<>();

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractAggregate() {}

  /**
   * Instantiates a new Abstract aggregate.
   *
   * @param id
   *   the id
   */
  protected AbstractAggregate(@NonNull ID id) {
    super(id);
  }

  /**
   * Registers domain events.
   *
   * @param event
   *   the event
   */
  protected void registerEvent(@NonNull DomainEvent<?, ID, THIS, ?> event) {
    this.domainEvents.add(event);
    this.markDirty();
  }

  /**
   * Clears domain events.
   */
  @AfterDomainEventPublication
  protected void clearDomainEvents() {
    this.domainEvents.clear();
  }

  /**
   * Domain events collection.
   *
   * @return the collection
   */
  @DomainEvents
  protected @NonNull Collection<DomainEvent<?, ID, THIS, ?>> domainEvents() {
    return Collections.unmodifiableList(this.domainEvents);
  }
}
