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
  implementation(sbd4.spring.boot.autoconfigure)
  implementation(sbd4.spring.context)
  runtimeOnly(sbd4.spring.test)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        compileOnly(sbd4.spring.test)
        implementation(sbd4.spring.boot.test)
      }
    }
  }
}
