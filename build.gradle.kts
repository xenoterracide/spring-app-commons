import org.semver4j.Semver

// SPDX-FileCopyrightText: Copyright © 2024-2026 Caleb Cushing
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
        exclude(sbd4.junit.jupiter.params)
        exclude(sbd4.junit.jupiter)
        exclude(sbd4.assertj.core)
        exclude(sbd4.jspecify)
      }
    }
    // buildHealth does not understand JPMS module-info requires
    project(":test-authorization-server") {
      onUnusedDependencies {
        exclude(sbd4.spring.security.oauth2.authorization.server)
      }
      onIncorrectConfiguration {
        exclude(sbd4.spring.security.oauth2.core)
      }
    }
    // spring-boot-data-jpa-test has runtime components needed beyond compile-only
    project(":commons-jpa") {
      onCompileOnly {
        exclude(sbd4.spring.boot.data.jpa.test)
      }
    }
    project(":commons-model") {
      onCompileOnly {
        exclude(sbd4.spring.boot.data.jpa.test)
      }
    }
    project(":security-model") {
      onUnusedDependencies {
        exclude(sbd4.spring.boot.test.autoconfigure)
      }
      onCompileOnly {
        exclude(sbd4.spring.boot.data.jpa.test)
      }
    }
  }
}
