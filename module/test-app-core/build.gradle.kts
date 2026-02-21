// © 2023, 2024 Copyright Caleb Cushing
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

dependencies {
  implementation(sbd3.spring.boot.autoconfigure)
  implementation(sbd3.spring.context)
  runtimeOnly(sbd3.spring.test)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        compileOnly(sbd3.spring.test)
        implementation(sbd3.spring.boot.test)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation.bundle(sbd3.bundles.test.impl)
        runtimeOnly.bundle(sbd3.bundles.test.runtime)
      }
    }
  }
}
