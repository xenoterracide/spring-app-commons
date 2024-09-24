// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

tasks.javadoc {
  enabled = false
}

coverage {
  minimum = 0.3
}

dependencies {
  api(libs.spring.boot.autoconfigure)
  api(libs.spring.context)

  implementation(libs.spring.boot.core)

  runtimeOnly(projects.securityController)
  runtimeOnly(libs.starter.actuator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.test)
        implementation(libs.spring.boot.test.core)

        runtimeOnly(libs.jmolecules.architecture.layered)

        runtimeOnly(libs.h2)
      }
    }
  }
}
