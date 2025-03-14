// Copyright 2024 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0

package com.xenoterracide.commons.model;

import org.jspecify.annotations.Nullable;

/**
 * Interface for objects that have a name.
 *
 * @apiNote you must implement one of {@link #getName()} or {@link #name()}.
 */
public interface Nameable {
  /**
   * Gets the name of the object.
   *
   * @return the name of the object.
   */
  default @Nullable String getName() {
    return this.name();
  }

  /**
   * Gets the name of the object.
   *
   * @return the name of the object.
   */
  default @Nullable String name() {
    return this.getName();
  }
}
