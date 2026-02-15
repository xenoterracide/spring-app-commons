// SPDX-FileCopyrightText: Copyright © 2023 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  id("com.xenoterracide.gradle.convention.test")
}

val libs = the<LibrariesForLibs>()

dependencies {
  testFixturesImplementation(platform(libs.jakarta.bom))
  testFixturesImplementation(platform(libs.spring.bom))
}

testing {
  suites {
    withType<JvmTestSuite>().configureEach {
      dependencies {
        compileOnly(libs.jmolecules.architecture.layered)
        compileOnly(platform(libs.jakarta.bom))
        compileOnly(platform(libs.jmolecules.bom))
        compileOnly(platform(libs.junit.bom))
        compileOnly(platform(libs.spring.bom))
        compileOnly(platform(libs.spring.modulith.bom))
        compileOnly(libs.jspecify)
        implementation(platform(libs.jakarta.bom))
        implementation(platform(libs.jmolecules.bom))
        implementation(platform(libs.junit.bom))
        implementation(platform(libs.spring.bom))
        implementation(platform(libs.spring.modulith.bom))
        runtimeOnly(platform(libs.jakarta.bom))
        runtimeOnly(platform(libs.jmolecules.bom))
        runtimeOnly(platform(libs.junit.bom))
        runtimeOnly(platform(libs.spring.bom))
        runtimeOnly(platform(libs.spring.modulith.bom))

        implementation.addConstraint(constraint(libs.jboss.logging))
      }
    }
  }
}
