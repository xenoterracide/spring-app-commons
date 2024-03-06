// SPDX-License-Identifier: MIT
// © Copyright 2024 Caleb Cushing. All rights reserved.

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.spotless
  alias(libs.plugins.dependency.analysis)
}

dependencyLocking.lockAllConfigurations()

group = "com.xenoterracide"
version = "0.1.0-SNAPSHOT"

tasks.dependencies {
  dependsOn(subprojects.map { "${it.path}:dependencies" })
}

dependencyAnalysis {
  issues {
    all {
      onAny {
        severity("fail")
      }
      onUnusedDependencies {
        exclude(libs.junit.parameters)
      }
    }
  }
}
