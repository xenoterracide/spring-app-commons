// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test.authorization.server;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.HttpComponentsClientHttpConnector;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClientConfigurer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

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

  String client = "client";

  @Test
  void authn() throws IOException, InterruptedException {
    var props = System.getProperties();
    assertThat(props).describedAs("props").isNotNull();

    var rf = new HttpComponentsClientHttpRequestFactory();
    rf.setHttpClient(HttpClients.custom().disableRedirectHandling().build());

    var restClient = RestClient.builder()
      .requestFactory(rf)
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

    var authorize = restClient
      .get()
      .uri(uriBuilder -> {
        return uriBuilder
          .path(this.authorizationUriPath)
          .queryParam(OAuth2ParameterNames.CLIENT_ID, this.client)
          .queryParam(OAuth2ParameterNames.SCOPE, "openid+profile+email")
          .queryParam(OAuth2ParameterNames.REDIRECT_URI, AuthorizationServer.REDIRECT_URI)
          .queryParam(OAuth2ParameterNames.RESPONSE_TYPE, "code")
          .queryParam(OAuth2ParameterNames.STATE, "sUmww5GH")
          .queryParam("audience", "http://localhost")
          .queryParam("response_mode", "query")
          .queryParam("nonce", "FVO5cA3")
          .queryParam("code_challenge", "g0bA5")
          .queryParam("code_challenge_method", "S256")
          .queryParam("auth0Client", "eyJuY")
          .build();
      })
      .retrieve()
      .toEntity(String.class);

    assertThat(authorize.getStatusCode()).describedAs("authorize").isEqualTo(HttpStatus.OK);

    // token
    // client_id: sOBUXAlH5Lb2mIRE02r4uRgkPMXcJK3Z
    //code_verifier: fhXx_RrpXnth36LK6DbfE4WQFrX2AW21.k9NWgnRD16
    //grant_type: authorization_code
    //code: NqiG26McQMoLlVAj5_4iKE2KaTsgzGU9r9W3_LQ29UMao
    //redirect_uri: http://localhost:3000

    var params = new LinkedMultiValueMap<String, String>();
    params.add(OAuth2ParameterNames.CLIENT_ID, this.client);
    params.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
    params.add(OAuth2ParameterNames.CODE, "NqiG26McQMoLlVAj5_4iKE2KaTsgzGU9r9W3_LQ29UMao");
    params.add(OAuth2ParameterNames.REDIRECT_URI, AuthorizationServer.REDIRECT_URI);
    params.add("code_verifier", "fhXx_RrpXnth36LK6DbfE4WQFrX2AW21.k9NWgnRD16");

    var tokenResponse = restClient
      .post()
      .uri(this.tokenUriPath)
      .body(params)
      .retrieve()
      .toEntity(OAuth2AccessTokenResponse.class);

    assertThat(tokenResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(tokenResponse.getBody().getAccessToken()).isNotNull();
  }

  @TestConfiguration
  public static class Config implements WebTestClientConfigurer {

    @Override
    public void afterConfigurerAdded(
      WebTestClient.Builder builder,
      WebHttpHandlerBuilder httpHandlerBuilder,
      ClientHttpConnector connector
    ) {
      builder.clientConnector(new HttpComponentsClientHttpConnector());
    }
  }
}
