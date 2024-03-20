// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import org.jspecify.annotations.NonNull;

/**
 * provides enough data to identify an entity.
 *
 * @param <ID> the identifier type
 * @param <ENTITY> the entity type
 */
public interface EntityIdentifiable<ID extends AbstractIdentity<?>, ENTITY extends AbstractEntity<ID>> {
  /**
   * Gets id.
   *
   * @return id
   */
  @NonNull
  ID entityId();

  /**
   * Gets type.
   *
   * @return class of the entity
   */
  @NonNull
  Class<ENTITY> type();
}
