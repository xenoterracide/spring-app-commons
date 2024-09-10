// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.registration.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureGraphQlTester
class RegistrationCtrlrTest {

  @Autowired
  GraphQlTester graphQlTester;

  @Test
  void register() {
    this.graphQlTester.documentName("RegisterUser")
      .execute()
      .path("registerUser.id")
      .entity(String.class)
      .satisfies(id -> assertThat(id).isNotBlank());
  }
}
