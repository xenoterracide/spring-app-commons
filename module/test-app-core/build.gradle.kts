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
  implementation(sb.spring.boot.autoconfigure)
  implementation(sb.spring.context)
  runtimeOnly(sb.spring.test)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        compileOnly(sb.spring.test)
        implementation(sb.spring.boot.test)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation.bundle(sb.bundles.test.impl)
        runtimeOnly.bundle(sb.bundles.test.runtime)
      }
    }
  }
}
