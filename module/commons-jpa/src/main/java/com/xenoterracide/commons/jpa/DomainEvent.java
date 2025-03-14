// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa;

import com.xenoterracide.commons.model.EntityIdentifier;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

/**
 * A domain event.
 *
 * @param <EVENTID>
 *   the type parameter
 * @param <AID>
 *   the type parameter
 * @param <AGG>
 *   the type parameter
 * @param <PAYLOAD>
 *   the type parameter
 * @param id
 *   the id
 * @param occurredOn
 *   the time the event occurred
 * @param payload
 *   the event payload
 * @param aggregate
 *   the aggregate the event is associated with
 */
public record DomainEvent<
  EVENTID extends Serializable,
  AID extends Identifier & Serializable,
  AGG extends AggregateRoot<AGG, AID>,
  PAYLOAD extends EntityIdentifier<?, ?>
>(
  @NonNull EVENTID id,
  @NonNull ZonedDateTime occurredOn,
  @NonNull EntityIdentifier<AID, AGG> aggregate,
  @NonNull PAYLOAD payload
) {}
