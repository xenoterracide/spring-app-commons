// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(sbd4.spring.data.commons)
  api(libs.jmolecules.ddd)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.java.tools)
        implementation(sbd4.jakarta.persistence.api)
        implementation(sbd4.spring.beans)
        implementation(sbd4.spring.boot.test.autoconfigure)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sbd4.h2)
        runtimeOnly(sbd4.spring.boot.starter.data.jpa)
        runtimeOnly.bundle(libs.bundles.jakarta.transaction)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation.bundle(sbd4.bundles.test.impl)
        runtimeOnly.bundle(sbd4.bundles.test.runtime)
      }
    }
  }
}
