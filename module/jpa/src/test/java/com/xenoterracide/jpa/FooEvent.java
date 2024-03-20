// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

interface FooEvent<PAYLOAD extends EntityIdentifiable<?, ?>>
  extends DomainEvent<@NonNull UUID, FooAggregate.@NonNull Id, @NonNull FooAggregate, @NonNull PAYLOAD> {}
