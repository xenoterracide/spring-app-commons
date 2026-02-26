// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user;

import jakarta.mail.internet.InternetAddress;
import java.net.URI;
import org.immutables.builder.Builder;

@Builder
public record RegisterNewUser(URI issuer, OIDCSubject subject, InternetAddress email, boolean emailVerified) {
  public static RegisterNewUserBuilder builder() {
    return new RegisterNewUserBuilder();
  }
}
