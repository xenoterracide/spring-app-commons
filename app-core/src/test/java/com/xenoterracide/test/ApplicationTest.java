// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide.test;

import com.xenoterracide.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;

@SpringBootTest
class ApplicationTest {

  Logger log = LogManager.getLogger(ApplicationTest.class);

  @Test
  void contextLoads() {
    // empty
  }

  @Test
  void modulith() {
    ApplicationModules.of(Application.class).verify();
    ApplicationModules.of(Application.class).stream().forEach(module -> log.info("module {}", module));
  }
}
