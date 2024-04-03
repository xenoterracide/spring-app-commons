package com.xenoterracide.test.authorization.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationServer {

  AuthorizationServer() {}

  public static void main(String[] args) {
    SpringApplication.run(AuthorizationServer.class, args);
  }
}
