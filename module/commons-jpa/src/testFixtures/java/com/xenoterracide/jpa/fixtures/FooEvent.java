// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa.fixtures;

import com.xenoterracide.jpa.AggregateIdentifier;
import com.xenoterracide.jpa.DomainEvent;
import com.xenoterracide.model.EntityIdentifier;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public final class FooEvent {

  private FooEvent() {}

  public static <PAYLOAD extends EntityIdentifier<?, ?>> DomainEvent<
    UUID,
    FooAggregate.Id,
    FooAggregate,
    PAYLOAD
  > create(FooAggregate.Id aggregateId, PAYLOAD payload) {
    return new DomainEvent<>(
      UUID.randomUUID(),
      ZonedDateTime.now(ZoneId.systemDefault()),
      new AggregateIdentifier<>(FooAggregate.class, aggregateId),
      payload
    );
  }
}
