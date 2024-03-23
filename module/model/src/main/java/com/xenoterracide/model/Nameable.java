// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model;

import org.jspecify.annotations.Nullable;

public interface Nameable {
  default @Nullable String getName() {
    return this.name();
  }

  default @Nullable String name() {
    return this.getName();
  }
}
