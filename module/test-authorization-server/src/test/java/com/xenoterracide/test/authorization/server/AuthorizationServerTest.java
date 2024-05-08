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

  @Value("${spring.security.user.name}")
  String user;

  @Value("${spring.security.user.password}")
  String pass;

  @Test
  void noAuth() {
    var restClient = RestClient.builder()
      .requestFactory(new HttpComponentsClientHttpRequestFactory())
      .baseUrl("http://localhost:" + this.port)
      .messageConverters(converters -> converters.addFirst(new OAuth2AccessTokenResponseHttpMessageConverter()))
      .build();

    var params = new LinkedMultiValueMap<String, String>();
    // assume AUTHORIZATION_CODE is right for PKCE...
    params.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    params.add(OAuth2ParameterNames.SCOPE, "openid");
    params.add(OAuth2ParameterNames.SCOPE, "profile");
    params.add(OAuth2ParameterNames.CLIENT_ID, this.user);

    var tokenResponse = restClient
      .post()
      .uri("/oauth2/token")
      .body(params)
      .retrieve()
      .toEntity(OAuth2AccessTokenResponse.class);

    assertThat(tokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(tokenResponse.getBody().getAccessToken()).isNotNull();
  }
}
