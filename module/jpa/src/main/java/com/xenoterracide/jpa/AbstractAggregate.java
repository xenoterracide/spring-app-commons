// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
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
 * @param <ID> the type parameter
 */
@MappedSuperclass
public abstract class AbstractAggregate<ID extends AbstractIdentity<? extends Serializable>>
  extends AbstractEntity<ID> {

  private final transient @Transient List<Object> domainEvents = new ArrayList<>();

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
   * Registers the given event object for publication on a call to a Spring Data repository's save or delete methods.
   *
   * @param <T>   the type of event
   * @param event must not be {@literal null}.
   * @return the event that has been added.
   */
  protected <T> @NonNull T registerEvent(@NonNull T event) {
    this.domainEvents.add(event);
    return event;
  }

  /**
   * Clears all domain events currently held. Usually invoked by the infrastructure in place in Spring Data
   * repositories.
   */
  @AfterDomainEventPublication
  protected void clearDomainEvents() {
    this.domainEvents.clear();
  }

  /**
   * All domain events currently captured by the aggregate.
   *
   * @return the collection
   */
  @DomainEvents
  protected Collection<Object> domainEvents() {
    return Collections.unmodifiableList(domainEvents);
  }
}
