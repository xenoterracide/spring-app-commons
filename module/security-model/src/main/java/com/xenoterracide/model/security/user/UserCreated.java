// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import org.axonframework.eventsourcing.annotation.EventTag;
import org.immutables.builder.Builder;

@Builder
public record UserCreated(@EventTag User.UserId id) {
  public static UserCreatedBuilder builder() {
    return new UserCreatedBuilder();
  }
}
