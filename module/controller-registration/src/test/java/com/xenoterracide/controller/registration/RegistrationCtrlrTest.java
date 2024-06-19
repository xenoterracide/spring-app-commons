// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.controller.registration;

import com.xenoterracide.model.security.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest
class RegistrationCtrlrTest {

  @MockBean
  UserRepository userRepository;

  @Autowired
  GraphQlTester graphQlTester;

  @Test
  void register() {
    this.graphQlTester.document("RegisterUser")
      .execute()
      .path("registerUser.id")
      .entity(String.class)
      .isEqualTo("Hello, Alice!");
  }
}
