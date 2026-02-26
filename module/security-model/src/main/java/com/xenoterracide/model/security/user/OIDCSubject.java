// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import java.util.Objects;

public record OIDCSubject(String raw) {
  public OIDCSubject {
    Objects.requireNonNull(raw);
  }

  @Override
  public String toString() {
    return raw;
  }
}
