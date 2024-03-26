// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.xenoterracide.model.EntityIdentifier;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.jspecify.annotations.NonNull;

/**
 * A domain event.
 *
 * @param <EVENTID> the type parameter
 * @param <AID> the type parameter
 * @param <AGG> the type parameter
 * @param <PAYLOAD> the type parameter
 * @param id  the id
 * @param occurredOn the time the event occurred
 * @param payload the event payload
 * @param aggregate the aggregate the event is associated with
 */
public record DomainEvent<
  EVENTID extends Serializable,
  AID extends AbstractIdentitifier,
  AGG extends AbstractAggregate<AID, ?>,
  PAYLOAD extends EntityIdentifier<?, ?>
>(
  @NonNull EVENTID id,
  @NonNull ZonedDateTime occurredOn,
  @NonNull EntityIdentifier<AID, AGG> aggregate,
  @NonNull PAYLOAD payload
) {}
