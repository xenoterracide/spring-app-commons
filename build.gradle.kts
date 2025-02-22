import org.semver4j.Semver

// SPDX-FileCopyrightText: Copyright © 2024 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `lifecycle-base`
  alias(libs.plugins.dependency.analysis)
  alias(libs.plugins.semver)
}

dependencyLocking { lockAllConfigurations() }

group = "com.xenoterracide"
version =
  providers
    .environmentVariable("IS_PUBLISHING")
    .flatMap { semver.provider }
    .getOrElse(Semver.ZERO)

tasks.dependencies {
  dependsOn(subprojects.map { it.tasks.dependencies })
}

tasks.check {
  dependsOn(tasks.buildHealth)
}

dependencyAnalysis {
  issues {
    all {
      onAny {
        severity("fail")
      }
      onUnusedDependencies {
        exclude(libs.junit.parameters)
        exclude(libs.assertj)
        exclude(libs.spring.test)
        exclude(libs.spring.boot.test.autoconfigure)
        exclude(libs.spring.boot.test.core)
        exclude(libs.jspecify)
      }
    }
  }
}
