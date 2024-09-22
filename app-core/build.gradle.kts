// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
  alias(libs.plugins.java.module.testing)
}

tasks.javadoc {
  enabled = false
}

dependencies {
  api(platform(libs.spring.modulith.bom))

  api(libs.spring.boot.core)
  api(libs.spring.modulith.core)
  api(libs.spring.boot.autoconfigure)

  implementation(libs.spring.context)

  runtimeOnly(projects.securityController)
  runtimeOnly(libs.starter.actuator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      withType<JvmTestSuite>().configureEach {
        dependencies {
          implementation(project())
        }
      }
      dependencies {
        implementation(platform(libs.jmolecules.bom))
        implementation(libs.spring.test)
        implementation(libs.spring.boot.test.core)
        runtimeOnly(libs.h2)
      }
    }
  }
}
