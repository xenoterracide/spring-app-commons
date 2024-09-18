// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test;

import com.xenoterracide.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class ApplicationTest {

  @Test
  void contextLoads() {}

  @Test
  void modules() {
    ApplicationModules.of(Application.class).verify();
  }
}
