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
  api(sb.spring.boot.autoconfigure)
  api(sb.spring.context)
  implementation(sb.spring.boot)
  runtimeOnly(projects.securityController)
  runtimeOnly(sb.spring.boot.starter.actuator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.modulith.core)
        implementation(sb.log4j.api)
        implementation(sb.spring.boot.test)
        implementation.bundle(sb.bundles.test.impl)
        runtimeOnly(libs.jmolecules.architecture.layered)
        runtimeOnly(sb.h2)
        runtimeOnly.bundle(sb.bundles.test.runtime)
      }
    }
  }
}

dependencies {
  runtimeOnly(sb.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
