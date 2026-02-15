// SPDX-FileCopyrightText: Copyright © 2023 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  id("our.bom")
  id("our.javatest")
  `java-library`
}

val libs = the<LibrariesForLibs>()

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(platform(libs.immutables.bom))
  annotationProcessor(libs.immutables.core)
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(platform(libs.immutables.bom))
  compileOnly(libs.bundles.immutables)

  api(libs.jmolecules.ddd)

  implementation(platform(libs.spring.modulith.bom))
  implementation(libs.uuid.creator)
  implementation(libs.spring.modulith.api)

  runtimeOnly(libs.bundles.jakarta.transaction)

  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesAnnotationProcessor(platform(libs.immutables.bom))
  testFixturesAnnotationProcessor(libs.immutables.core)

  testFixturesCompileOnly(platform(libs.immutables.bom))
  testFixturesCompileOnly(libs.bundles.immutables)
  testFixturesCompileOnly(libs.jspecify)
}

testing {
  suites {
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))
        implementation(platform(libs.jakarta.bom))
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.modulith.test)
      }
    }
    val testWhitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(libs.archunit.core)
        implementation(libs.archunit.junit.api)
        implementation(libs.equalsverifier)
        implementation(libs.jmolecules.archunit)
      }
    }
  }
}

val jpaModelGen =
  listOf(
    "-AaddSuppressWarningsAnnotation=deprecation,rawtypes,missing-explicit-ctor",
    "-AaddGeneratedAnnotation=true",
  )
tasks.compileJava {
  options.compilerArgs.addAll(jpaModelGen)
}
