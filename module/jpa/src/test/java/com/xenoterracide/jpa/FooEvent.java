// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.xenoterracide.model.EntityIdentifier;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public final class FooEvent {

  private FooEvent() {}

  static <PAYLOAD extends EntityIdentifier<?, ?>> DomainEvent<
    @NonNull UUID,
    FooAggregate.@NonNull Id,
    @NonNull FooAggregate,
    @NonNull PAYLOAD
  > create(FooAggregate.@NonNull Id aggregateId, @NonNull PAYLOAD payload) {
    return new DomainEvent<>(
      UUID.randomUUID(),
      ZonedDateTime.now(ZoneId.systemDefault()),
      new AggregateIdentifier<>(FooAggregate.class, aggregateId),
      payload
    );
  }
}
