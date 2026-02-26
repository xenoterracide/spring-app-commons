// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import org.axonframework.eventsourcing.annotation.EventTag;
import org.immutables.builder.Builder;

@Builder
public record IdentityProviderAssociated(@EventTag User.UserId id) {
  public static IdentityProviderAssociatedBuilder builder() {
    return new IdentityProviderAssociatedBuilder();
  }
}
