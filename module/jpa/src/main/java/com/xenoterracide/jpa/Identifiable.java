// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Persistable;

/**
 * An entity that has an identifier.
 *
 * @param <ID> the type of the identifier.
 */
public interface Identifiable<ID> extends Persistable<ID> {
  @Override
  @Nullable
  ID getId();

  @Override
  default boolean isNew() {
    return this.getId() == null;
  }
}
