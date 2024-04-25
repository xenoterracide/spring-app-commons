// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.authn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource(
  value = "file:///${user.home}/.config/spring-boot/application-${spring.application.name}.properties",
  ignoreResourceNotFound = true
)
public class OidcFakeControllerTest {

  @Test
  void noAuth() {}
}
