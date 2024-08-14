// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import java.io.Serializable;
import org.jspecify.annotations.NonNull;

/**
 * Interface for objects that have an identifier.
 *
 * @param <ID> the type of the identifier
 * @param <ENTITY> the type of the entity
 */
public interface EntityIdentifier<ID extends @NonNull Serializable, ENTITY extends Identifiable<@NonNull ID>>
  extends Identifiable<@NonNull ID> {
  /**
   * Gets type.
   *
   * @return class of the entity
   */
  @NonNull
  Class<ENTITY> type();
}
