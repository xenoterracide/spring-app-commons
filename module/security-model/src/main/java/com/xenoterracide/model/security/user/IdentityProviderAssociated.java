// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import jakarta.mail.internet.InternetAddress;
import java.net.URI;
import org.axonframework.eventsourcing.annotation.EventTag;
import org.immutables.builder.Builder;
import org.immutables.datatype.Data;

@Data
@Builder
public record IdentityProviderAssociated(
  @EventTag User.UserId id,
  URI issuer,
  OIDCSubject subject,
  InternetAddress email,
  boolean emailVerified
) {
  public static IdentityProviderAssociatedBuilder builder() {
    return new IdentityProviderAssociatedBuilder();
  }
}
