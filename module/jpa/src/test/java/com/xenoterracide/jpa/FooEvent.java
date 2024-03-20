// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record FooEvent<PAYLOAD extends EntityIdentifiable<?, ?>>(
  UUID id,
  ZonedDateTime occurredOn,
  FooAggregate.Id entityId,

  PAYLOAD payload
)
  implements DomainEvent<@NonNull UUID, FooAggregate.@NonNull Id, @NonNull FooAggregate, @NonNull PAYLOAD> {
  static <PAYLOAD extends EntityIdentifiable<?, ?>> FooEvent<PAYLOAD> create(
    FooAggregate.Id aggregateId,
    PAYLOAD payload
  ) {
    return new FooEvent<>(UUID.randomUUID(), ZonedDateTime.now(ZoneId.systemDefault()), aggregateId, payload);
  }

  @Override
  public @NonNull Class<@NonNull FooAggregate> type() {
    return FooAggregate.class;
  }
}
