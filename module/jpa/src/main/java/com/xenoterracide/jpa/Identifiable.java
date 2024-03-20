// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import org.jspecify.annotations.Nullable;

/**
 * An entity that has an identifier.
 *
 * @param <ID> the type of the identifier.
 */
public interface Identifiable<ID> {
  /**
   * Gets the identifier of this entity.
   *
   * @return the identifier of this entity
   */
  @Nullable
  ID getId();
}
