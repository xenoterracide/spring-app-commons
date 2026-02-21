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
  api(sbd3.spring.boot.autoconfigure)

  implementation(platform(libs.spring.bom))
  implementation(sbd3.spring.boot)
  implementation(sbd3.spring.context)
  implementation(sbd3.spring.security.oauth2.authorization.server)
  implementation(sbd3.spring.security.oauth2.core)
  implementation(sbd3.spring.core)
  implementation(sbd3.spring.security.config)
  implementation(sbd3.spring.security.web)
  implementation(sbd3.spring.web)

  compileOnly(libs.java.tools)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(sbd3.spring.boot.starter.oauth2.authorization.server)
  runtimeOnly(sbd3.spring.boot.starter.security)
  runtimeOnly(sbd3.spring.boot.starter.web)
  runtimeOnly(sbd3.spring.boot.devtools)
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd3.httpclient5)
        implementation(sbd3.spring.test)
        implementation(sbd3.spring.boot.test)
        implementation(sbd3.spring.beans)
        runtimeOnly(platform(libs.spring.bom))
        runtimeOnly(projects.testAppCore)
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
