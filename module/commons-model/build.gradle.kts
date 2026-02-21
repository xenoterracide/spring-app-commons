// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(sbd3.spring.data.commons)
  api(libs.jmolecules.ddd)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.java.tools)
        implementation(sbd3.jakarta.persistence.api)
        implementation(sbd3.spring.beans)
        implementation(sbd3.spring.boot.test.autoconfigure)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sbd3.h2)
        runtimeOnly(sbd3.spring.boot.starter.data.jpa)
        runtimeOnly.bundle(libs.bundles.jakarta.transaction)
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
