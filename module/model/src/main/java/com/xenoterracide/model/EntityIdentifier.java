// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import java.io.Serializable;
import org.jspecify.annotations.NonNull;

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
