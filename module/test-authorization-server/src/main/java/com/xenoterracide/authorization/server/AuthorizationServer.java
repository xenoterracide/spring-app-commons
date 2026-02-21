// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.authorization.server;

import com.xenoterracide.tools.java.annotation.ExcludeFromGeneratedCoverageReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Test Authorization Server to mimick Auth0.
 */
@SpringBootApplication(proxyBeanMethods = false)
public class AuthorizationServer {

  /**
   * Client ID for the client.
   */
  public static final String CLIENT_ID = "client";
  /**
   * Redirect URI for the client.
   */
  public static final String REDIRECT_URI = "http://localhost:3000";

  AuthorizationServer() {}

  /**
   * Main.
   *
   * @param args
   *   arguments to the program
   */
  @ExcludeFromGeneratedCoverageReport
  static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http
      .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
      .formLogin(Customizer.withDefaults())
      .oauth2AuthorizationServer(
        authorizationServer -> authorizationServer.oidc(Customizer.withDefaults()) // Enable OpenID Connect 1.0
      );
    return http.build();
  }
}
