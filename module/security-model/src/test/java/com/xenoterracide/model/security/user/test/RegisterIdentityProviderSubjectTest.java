// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.model.security.user.test;

import com.xenoterracide.model.security.user.OIDCSubject;
import com.xenoterracide.model.security.user.RegisterNewUser;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.net.URI;
import org.axonframework.eventsourcing.configuration.EventSourcingConfigurer;
import org.axonframework.test.fixture.AxonTestFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegisterIdentityProviderSubjectTest {

  private AxonTestFixture fixture;

  @BeforeEach
  void beforeEach() {
    fixture = AxonTestFixture.with(EventSourcingConfigurer.create());
  }

  @AfterEach
  void afterEach() {
    fixture.stop();
  }

  @Test
  void register() throws AddressException {
    var registration = RegisterNewUser.builder()
      .subject(new OIDCSubject("google:12345"))
      .issuer(URI.create("https://example.com"))
      .email(new InternetAddress("xenoterracide@gmail.com"))
      .emailVerified(true)
      .build();
  }
}
