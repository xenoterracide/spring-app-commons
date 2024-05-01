package com.xenoterracide.test.authorization.server;

import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@SpringBootApplication(proxyBeanMethods = false)
public class AuthorizationServer {

  AuthorizationServer() {}

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }

  @Bean
  RegisteredClientRepository registeredClientRepository() {
    var publicClient = RegisteredClient.withId(UUID.randomUUID().toString())
      .clientId("public-client")
      .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
      .redirectUri("http://127.0.0.1:4200")
      .scope(OidcScopes.OPENID)
      .scope(OidcScopes.PROFILE)
      .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).requireProofKey(true).build())
      .build();

    return new InMemoryRegisteredClientRepository(publicClient);
  }
}
