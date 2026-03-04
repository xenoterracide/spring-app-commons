// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import org.axonframework.eventsourcing.annotation.EventTag;
import org.immutables.builder.Builder;
import org.immutables.datatype.Data;

@Builder
@Data
public record UserCreated(@EventTag User.UserId id, @EventTag String name) {
  public static UserCreatedBuilder builder() {
    return new UserCreatedBuilder();
  }
}
