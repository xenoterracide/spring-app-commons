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
  api(sbd3.spring.boot.autoconfigure)
  api(sbd3.spring.context)
  implementation(sbd3.spring.boot)
  runtimeOnly(projects.securityController)
  runtimeOnly(sbd3.spring.boot.starter.actuator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.modulith.core)
        implementation(sbd3.log4j.api)
        implementation(sbd3.spring.boot.test)
        implementation.bundle(sbd3.bundles.test.impl)
        runtimeOnly(libs.jmolecules.architecture.layered)
        runtimeOnly(sbd3.h2)
        runtimeOnly.bundle(sbd3.bundles.test.runtime)
      }
    }
  }
}

dependencies {
  runtimeOnly(sbd3.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
