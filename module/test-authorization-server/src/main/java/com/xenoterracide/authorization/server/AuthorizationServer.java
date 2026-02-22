// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
//
// SPDX-License-Identifier: (AGPL-3.0-or-later WITH Universal-FOSS-exception-1.0 AND CC-BY-4.0) OR CC-BY-NC-4.0
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.authorization.server;

import com.xenoterracide.tools.java.annotation.ExcludeFromGeneratedCoverageReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

/**
 * Test Authorization Server to mimick Auth0.
 */
@SpringBootApplication(proxyBeanMethods = false)
public class AuthorizationServer {

  /**
   * Client ID for the public client (authorization code flow).
   */
  public static final String CLIENT_ID = "client";
  /**
   * Redirect URI for the public client.
   */
  public static final String REDIRECT_URI = "http://localhost:3000";
  /**
   * Client ID for the confidential client (client credentials flow).
   */
  public static final String CONFIDENTIAL_CLIENT_ID = "confidential";
  /**
   * Client secret for the confidential client.
   */
  public static final String CONFIDENTIAL_CLIENT_SECRET = "secret";

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
  @Order(1)
  SecurityFilterChain authorizationServerFilterChain(HttpSecurity http) {
    http
      .securityMatcher("/oauth/**", "/.well-known/**", "/oauth2/**", "/userinfo")
      .csrf(csrf -> csrf.disable())
      .oauth2AuthorizationServer(authorizationServer -> authorizationServer.oidc(Customizer.withDefaults()))
      .exceptionHandling(exceptions ->
        exceptions.defaultAuthenticationEntryPointFor(
          new LoginUrlAuthenticationEntryPoint("/login"),
          new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
        )
      );
    return http.build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultFilterChain(HttpSecurity http) {
    http
      .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
      .csrf(csrf -> csrf.disable())
      .formLogin(Customizer.withDefaults());
    return http.build();
  }
}
