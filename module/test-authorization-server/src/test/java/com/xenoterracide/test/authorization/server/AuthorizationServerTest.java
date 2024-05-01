package com.xenoterracide.test.authorization.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorizationServerTest {

  @Test
  void noAuth() {
    assertThat(true).isTrue();
  }
}
