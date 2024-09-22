// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: AGPL-3.0-or-later

package com.xenoterracide;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.springframework.modulith.test.ApplicationModuleTest;

@ApplicationModuleTest(ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class ApplicationTest {

  @Test
  void writeDocumentationSnippets() {
    new Documenter(ApplicationModules.of(Application.class)).writeModulesAsPlantUml();
  }

  @Test
  void contextLoads() {}

  @Test
  void modules() {
    ApplicationModules.of(Application.class).verify();
  }
}
