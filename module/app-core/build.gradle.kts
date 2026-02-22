// © 2024 Copyright Caleb Cushing
// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
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
  api(libs.spring.modulith.api)
  api(sbd4.spring.boot.autoconfigure)
  api(sbd4.spring.context)
  implementation(sbd4.spring.boot)
  runtimeOnly(projects.securityController)
  runtimeOnly(sbd4.spring.boot.starter.actuator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.modulith.core)
        implementation(sbd4.log4j.api)
        implementation(sbd4.spring.boot.test)
        implementation.bundle(sbd4.bundles.test.impl)
        runtimeOnly(libs.jmolecules.architecture.layered)
        runtimeOnly(sbd4.h2)
        runtimeOnly.bundle(sbd4.bundles.test.runtime)
      }
    }
  }
}
