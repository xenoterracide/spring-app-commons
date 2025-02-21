// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.model;

import java.io.Serializable;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * An entity that has an identifier.
 *
 * @param <ID>
 *   the type of the identifier.
 */
public interface Identifiable<ID extends @NonNull Identifier & @NonNull Serializable>
  extends org.jmolecules.ddd.types.Identifiable<ID> {
  /**
   * Gets the identifier of this entity.
   *
   * @return the identifier of this entity
   */
  @Override
  default @Nullable ID getId() {
    return this.id();
  }

  /**
   * Gets the identifier of this entity.
   *
   * @return the identifier of this entity
   * @see #getId()
   */
  default @Nullable ID id() {
    return this.getId();
  }
}
