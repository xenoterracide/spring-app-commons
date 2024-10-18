// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
  our.jpamodel
}

dependencies {
  api(projects.commonsModel)
  api(projects.commonsJpa)

  implementation(libs.java.tools)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        runtimeOnly(projects.testAppCore)
      }
    }
    val whitebox by getting(JvmTestSuite::class) {
      dependencies {
        implementation(projects.commonsJpa)
        implementation(projects.commonsModel)
      }
    }
  }
}
