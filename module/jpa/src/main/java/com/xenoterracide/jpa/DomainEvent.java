// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import java.time.ZonedDateTime;
import org.jspecify.annotations.NonNull;

/**
 * The type Abstract domain event.
 *
 * @param <ID> identifier type
 * @param <PAYLOAD> payload type
 * @param <AID> Aggregate Identifier type
 * @param <AGG> Aggregate type
 */
public interface DomainEvent<
  ID, AID extends AbstractIdentity<?>, AGG extends AbstractAggregate<AID, ?>, PAYLOAD extends EntityIdentifiable<?, ?>
>
  extends EntityIdentifiable<AID, AGG> {
  /**
   * The unique identifier of the event.
   *
   * @return the id
   */
  @NonNull
  ID id();

  /**
   * Gets occurred on.
   *
   * @return the time the event occurred
   */
  @NonNull
  ZonedDateTime occurredOn();

  /**
   * Gets payload.
   *
   * @return the payload
   */
  @NonNull
  PAYLOAD payload();
}
