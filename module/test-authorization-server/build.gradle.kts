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
  api(sb.spring.boot.autoconfigure)

  implementation(platform(libs.spring.bom))
  implementation(sb.spring.boot)
  implementation(sb.spring.context)
  implementation(sb.spring.security.oauth2.authorization.server)
  implementation(sb.spring.security.oauth2.core)
  implementation(sb.spring.core)
  implementation(sb.spring.security.config)
  implementation(sb.spring.security.web)
  implementation(sb.spring.web)

  compileOnly(libs.java.tools)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(sb.spring.boot.starter.oauth2.authorization.server)
  runtimeOnly(sb.spring.boot.starter.security)
  runtimeOnly(sb.spring.boot.starter.web)
  runtimeOnly(sb.spring.boot.devtools)
}
testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sb.httpclient5)
        implementation(sb.spring.test)
        implementation(sb.spring.boot.test)
        implementation(sb.spring.beans)
        runtimeOnly(platform(libs.spring.bom))
        runtimeOnly(projects.testAppCore)
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
