// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record BarNameChangedEvent(
  UUID id,
  ZonedDateTime occurredOn,
  FooAggregate.Id entityId,

  Payload payload
)
  implements FooEvent<BarNameChangedEvent.Payload> {
  static BarNameChangedEvent create(FooAggregate.Id aggregateId, Payload payload) {
    return new BarNameChangedEvent(UUID.randomUUID(), ZonedDateTime.now(ZoneId.systemDefault()), aggregateId, payload);
  }

  @Override
  public @NonNull Class<@NonNull FooAggregate> type() {
    return FooAggregate.class;
  }

  record Payload(BarEntity.Id entityId, String name)
    implements EntityIdentifiable<BarEntity.@NonNull Id, @NonNull BarEntity> {
    @Override
    public @NonNull Class<BarEntity> type() {
      return BarEntity.class;
    }
  }
}
