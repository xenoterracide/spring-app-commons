// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

/**
 * An abstract class for Domain Aggregates.
 *
 * @param <ID> type parameter
 * @param <EVENT> type parameter
 */
@MappedSuperclass
public abstract class AbstractAggregate<ID extends AbstractIdentity, SELF extends AbstractAggregate<ID, ?>>
  extends AbstractEntity<ID> {

  private final @Transient List<DomainEvent<?, ID, SELF, ?>> domainEvents = new ArrayList<>();

  /**
   * NO-OP parent constuctor for JPA only.
   */
  protected AbstractAggregate() {}

  /**
   * Instantiates a new Abstract aggregate.
   *
   * @param id the id
   */
  protected AbstractAggregate(@NonNull ID id) {
    super(id);
  }

  /**
   * Registers domain events.
   *
   * @param <E> the event type
   * @param event the event
   */
  protected void registerEvent(@NonNull DomainEvent<?, ID, SELF, ?> event) {
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
  protected @NonNull Collection<DomainEvent<?, ID, SELF, ?>> domainEvents() {
    return Collections.unmodifiableList(this.domainEvents);
  }
}
