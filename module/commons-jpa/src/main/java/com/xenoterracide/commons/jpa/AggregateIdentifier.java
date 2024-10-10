// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0 OR CC-BY-NC-4.0

package com.xenoterracide.commons.jpa;

import com.xenoterracide.commons.model.EntityIdentifier;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jspecify.annotations.NonNull;

/**
 * An identifier for an Aggregate.
 *
 * @param <ID>
 *   the type of the identifier
 * @param <AGG>
 *   the type of the aggregate
 * @param id
 *   the identifier
 * @param type
 *   the type of the aggregate
 */
public record AggregateIdentifier<ID extends AbstractIdentitifier, AGG extends AggregateRoot<AGG, @NonNull ID>>(
  Class<AGG> type,
  ID id
)
  implements EntityIdentifier<@NonNull ID, @NonNull AGG> {}
