// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.jpa;

import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import org.jspecify.annotations.Nullable;

/**
 * An entity that has an identifier.
 *
 * @param <ID> the type of the identifier.
 */
public interface Identifiable<ID extends Serializable> {
  /**
   * Returns the identifier of this entity.
   *
   * @return the identifier of this entity. Nullable, but not blank.
   */
  @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
  @Nullable
  ID getId();
}
