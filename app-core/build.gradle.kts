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
  api(libs.spring.boot.core)
  api(libs.spring.modulith.core)
  api(libs.spring.boot.autoconfigure)

  implementation(libs.spring.context)

  runtimeOnly(projects.securityController)
  runtimeOnly(libs.starter.actuator)
  runtimeOnly(libs.starter.modulith.core)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {

        implementation(libs.spring.test)
        implementation(libs.spring.boot.test.core)
        implementation(libs.spring.modulith.docs)
        implementation(libs.spring.modulith.test)
        runtimeOnly(libs.jmolecules.architecture.layered)
        runtimeOnly(libs.starter.modulith.test)
        runtimeOnly(libs.h2)
      }
    }
  }
}
