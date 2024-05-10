// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test.authorization.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@ActiveProfiles({ "test", "test-http" })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizationServerTest {

  @LocalServerPort
  int port;

  @SuppressWarnings("NullAway")
  @Value("${spring.security.user.name}")
  String user;

  @SuppressWarnings("NullAway")
  @Value("${spring.security.user.password}")
  String pass;

  String client = "client";

  @Test
  void noAuth() {
    var restClient = RestClient.builder()
      .requestFactory(new HttpComponentsClientHttpRequestFactory())
      .baseUrl("http://localhost:" + this.port)
      .messageConverters(converters -> converters.addFirst(new OAuth2AccessTokenResponseHttpMessageConverter()))
      .build();

    var authorize = restClient
      .get()
      .uri(uriBuilder -> {
        return uriBuilder
          .path("/authorize")
          .queryParam("client_id", this.client)
          .queryParam("scope", "openid", "profile", "email")
          .queryParam("redirect_uri", "http://localhost:3000")
          .queryParam("audience", "http://localhost")
          .queryParam("response_type", "code")
          .queryParam("response_mode", "query")
          .queryParam("state", "sUmww5GH")
          .queryParam("nonce", "FVO5cA3")
          .queryParam("code_challenge", "g0bA5")
          .queryParam("code_challenge_method", "S256")
          .queryParam("auth0Client", "eyJuY")
          .build();
      })
      .retrieve()
      .toEntity(String.class);

    assertThat(authorize.getStatusCode()).describedAs("authorize").isEqualTo(HttpStatus.OK);

    var authn = new LinkedMultiValueMap<String, String>();
    authn.add("username", this.user);
    authn.add("password", this.pass);

    var login = restClient.post().uri("/login").body(authn).retrieve().toEntity(String.class);

    assertThat(login.getStatusCode()).describedAs("login").isEqualTo(HttpStatus.OK);

    var params = new LinkedMultiValueMap<String, String>();
    params.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    params.add(OAuth2ParameterNames.SCOPE, "openid");
    params.add(OAuth2ParameterNames.SCOPE, "profile");
    params.add(OAuth2ParameterNames.SCOPE, "email");
    params.add(OAuth2ParameterNames.CLIENT_ID, this.client);

    var tokenResponse = restClient
      .post()
      .uri("/oauth2/token")
      .headers(headers -> headers.setBasicAuth(this.user, this.pass)) // switching to basic auth
      .body(params)
      .retrieve()
      .toEntity(OAuth2AccessTokenResponse.class);

    assertThat(tokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(tokenResponse.getBody().getAccessToken()).isNotNull();
  }
}
