// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.accessors.dm.LibrariesForSbd4

plugins {
  id("com.xenoterracide.gradle.convention.test")
}

val libs = the<LibrariesForLibs>()
val sbd4 = the<LibrariesForSbd4>()

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
        compileOnly(sbd4.jspecify)
        implementation(platform(libs.jakarta.bom))
        implementation(platform(libs.jmolecules.bom))
        implementation(platform(libs.junit.bom))
        implementation(platform(libs.spring.bom))
        implementation(platform(libs.spring.modulith.bom))
        implementation.bundle(sbd4.bundles.test.impl)
        runtimeOnly(platform(libs.jakarta.bom))
        runtimeOnly(platform(libs.jmolecules.bom))
        runtimeOnly(platform(libs.junit.bom))
        runtimeOnly(platform(libs.spring.bom))
        runtimeOnly(platform(libs.spring.modulith.bom))
        runtimeOnly.bundle(sbd4.bundles.test.runtime)

        implementation.addConstraint(constraint(libs.jboss.logging))
      }
    }
  }
}
