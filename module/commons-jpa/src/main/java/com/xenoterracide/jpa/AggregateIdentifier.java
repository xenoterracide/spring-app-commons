// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import com.xenoterracide.model.EntityIdentifier;
import org.jspecify.annotations.NonNull;

/**
 * An identifier for an Aggregate.
 *
 * @param <ID> the type of the identifier
 * @param <AGG> the type of the aggregate
 * @param id the identifier
 * @param type the type of the aggregate
 */
public record AggregateIdentifier<ID extends AbstractIdentitifier, AGG extends AbstractAggregate<@NonNull ID, ?>>(
  Class<AGG> type,
  ID id
)
  implements EntityIdentifier<@NonNull ID, @NonNull AGG> {}
