// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.model;

import java.io.Serializable;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;
import org.jspecify.annotations.NonNull;

/**
 * Interface for objects that have an identifier.
 *
 * @param <ID>
 *   the type of the identifier
 * @param <ENTITY>
 *   the type of the entity
 */
public interface EntityIdentifier<
  ID extends @NonNull Identifier & @NonNull Serializable,
  ENTITY extends Entity<?, @NonNull ID>
>
  extends Identifiable<@NonNull ID> {
  /**
   * Gets type.
   *
   * @return class of the entity
   */
  @NonNull
  Class<ENTITY> type();
}
