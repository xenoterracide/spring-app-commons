// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(sb.spring.data.commons)
  api(libs.jmolecules.ddd)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.java.tools)
        implementation(sb.jakarta.persistence.api)
        implementation(sb.spring.beans)
        implementation(sb.spring.boot.test.autoconfigure)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sb.h2)
        runtimeOnly(sb.spring.boot.starter.data.jpa)
        runtimeOnly.bundle(libs.bundles.jakarta.transaction)
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
