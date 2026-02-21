// © 2024 Copyright Caleb Cushing
// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(platform(libs.spring.bom))
  api(sbd4.spring.boot.autoconfigure)

  implementation(platform(libs.spring.bom))
  implementation(sbd4.spring.boot)
  implementation(sbd4.spring.context)
  implementation(sbd4.spring.security.oauth2.authorization.server)
  implementation(sbd4.spring.security.oauth2.core)
  implementation(sbd4.spring.core)
  implementation(sbd4.spring.security.config)
  implementation(sbd4.spring.security.web)
  implementation(sbd4.spring.web)

  compileOnly(libs.java.tools)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(sbd4.spring.boot.starter.oauth2.authorization.server)
  runtimeOnly(sbd4.spring.boot.starter.security)
  runtimeOnly(sbd4.spring.boot.starter.web)
  runtimeOnly(sbd4.spring.boot.devtools)
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd4.httpclient5)
        implementation(sbd4.spring.test)
        implementation(sbd4.spring.boot.test)
        implementation(sbd4.spring.beans)
        runtimeOnly(platform(libs.spring.bom))
        runtimeOnly(projects.testAppCore)
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
