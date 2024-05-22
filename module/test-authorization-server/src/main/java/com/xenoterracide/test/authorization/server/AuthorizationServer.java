// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test.authorization.server;

import com.xenoterracide.tools.java.annotation.ExcludeFromGeneratedCoverageReport;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
  private static final String ALL = "*";

  AuthorizationServer() {}

  /**
   * Main.
   *
   * @param args arguments to the program
   */
  @ExcludeFromGeneratedCoverageReport
  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }

  @Bean
  @Order(1)
  SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults());
    http
      // Redirect to the login page when not authenticated from the
      // authorization endpoint
      .exceptionHandling(
        exceptions ->
          exceptions.defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint("/login"),
            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
          )
      )
      // Accept access tokens for User Info and/or Client Registration
      .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

    return http.cors(Customizer.withDefaults()).build();
  }

  @Bean
  @Order(2)
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorize -> authorize.requestMatchers("/oauth/authorize").permitAll())
      .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
      // Form login handles the redirect to the login page from the
      // authorization server filter chain
      .formLogin(Customizer.withDefaults());

    return http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable()).build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();
    config.addAllowedHeader(ALL);
    config.addAllowedMethod(ALL);
    config.addAllowedOrigin(REDIRECT_URI);
    config.setAllowCredentials(true);

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  RegisteredClientRepository registeredClientRepository() {
    var publicClient = RegisteredClient.withId(UUID.randomUUID().toString())
      .clientId(CLIENT_ID)
      .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .redirectUri(REDIRECT_URI)
      .scope(OidcScopes.OPENID)
      .scope(OidcScopes.PROFILE)
      .scope(OidcScopes.EMAIL)
      .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).requireProofKey(true).build())
      .build();

    return new InMemoryRegisteredClientRepository(publicClient);
  }
}
