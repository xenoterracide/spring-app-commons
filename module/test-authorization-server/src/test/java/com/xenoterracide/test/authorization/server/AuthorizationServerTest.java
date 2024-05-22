// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test.authorization.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.function.Consumer;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

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

  @SuppressWarnings("NullAway")
  @Value("${spring.security.oauth2.authorizationserver.endpoint.authorization-uri}")
  String authorizationUriPath;

  @SuppressWarnings("NullAway")
  @Value("${spring.security.oauth2.authorizationserver.endpoint.token-uri}")
  String tokenUriPath;

  SecureRandom random = new SecureRandom();
  Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();

  static byte[] bytesFrom(int size, Consumer<byte[]> setter) {
    var bytes = new byte[size];
    setter.accept(bytes);
    return bytes;
  }

  @Test
  void authn() throws Exception {
    var restClient = RestClient.builder()
      .requestFactory(
        new HttpComponentsClientHttpRequestFactory(HttpClients.custom().disableRedirectHandling().build())
      )
      .baseUrl("http://localhost:" + this.port)
      .messageConverters(converters -> {
        converters.addFirst(new OAuth2AccessTokenResponseHttpMessageConverter());
      })
      .build();

    var credentials = new LinkedMultiValueMap<String, String>();
    credentials.add("username", this.user);
    credentials.add("password", this.pass);

    var login = restClient
      .post()
      .uri("/login")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(credentials)
      .retrieve()
      .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {})
      .toEntity(String.class);

    assertThat(login).describedAs("login").extracting(res -> res.getStatusCode()).isEqualTo(HttpStatus.FOUND);

    var code = bytesFrom(32, random::nextBytes);
    var verifier = encoder.encodeToString(code);
    var challenge = encoder.encodeToString(
      MessageDigest.getInstance("SHA-256").digest(verifier.getBytes(StandardCharsets.US_ASCII))
    );

    var authorize = restClient
      .get()
      .uri(uriBuilder -> {
        return uriBuilder
          .path(this.authorizationUriPath)
          .queryParam(OAuth2ParameterNames.CLIENT_ID, AuthorizationServer.CLIENT_ID)
          .queryParam(OAuth2ParameterNames.SCOPE, "openid+profile+email")
          .queryParam(OAuth2ParameterNames.REDIRECT_URI, AuthorizationServer.REDIRECT_URI)
          .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, "code")
          .queryParam(OAuth2ParameterNames.STATE, "sUmww5GH")
          .queryParam(PkceParameterNames.CODE_CHALLENGE, challenge)
          .queryParam(PkceParameterNames.CODE_CHALLENGE_METHOD, "S256")
          .queryParam("auth0Client", "eyJuY")
          .queryParam("audience", "http://localhost")
          .queryParam("response_mode", "query")
          .queryParam("nonce", "FVO5cA3")
          .build();
      })
      .retrieve()
      .toEntity(String.class);

    assertThat(authorize.getStatusCode()).describedAs("authorize").isEqualTo(HttpStatus.FOUND);

    var qp = UriComponentsBuilder.fromUri(authorize.getHeaders().getLocation()).build().getQueryParams();

    assertThat(qp).describedAs("code").containsKey("code");

    var params = new LinkedMultiValueMap<String, String>();
    params.add(OAuth2ParameterNames.CLIENT_ID, AuthorizationServer.CLIENT_ID);
    params.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    params.add(OAuth2ParameterNames.CODE, qp.getFirst(OAuth2ParameterNames.CODE));
    params.add(OAuth2ParameterNames.REDIRECT_URI, AuthorizationServer.REDIRECT_URI);
    params.add(PkceParameterNames.CODE_VERIFIER, verifier);

    var tokenResponse = restClient
      .post()
      .uri(this.tokenUriPath)
      .body(params)
      .retrieve()
      .toEntity(OAuth2AccessTokenResponse.class);

    assertThat(tokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(tokenResponse.getBody().getAccessToken()).isNotNull();
  }
}
