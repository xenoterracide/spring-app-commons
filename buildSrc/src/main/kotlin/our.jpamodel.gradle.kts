// SPDX-FileCopyrightText: Copyright © 2023 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.accessors.dm.LibrariesForSb

plugins {
  id("our.bom")
  id("our.javacompile")
  id("our.javatest")
}

val libs = the<LibrariesForLibs>()
val sb = the<LibrariesForSb>()

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(platform(libs.immutables.bom))
  annotationProcessor(libs.immutables.core)
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(platform(libs.immutables.bom))
  compileOnly(libs.bundles.immutables)
  compileOnly(libs.hibernate.validator)

  api(libs.hibernate.envers)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.jmolecules.ddd)
  api(libs.spring.data.jpa)

  implementation(platform(libs.spring.modulith.bom))
  implementation(libs.uuid.creator)
  implementation(libs.spring.modulith.api)

  runtimeOnly(libs.starter.data.jpa)
  runtimeOnly(libs.starter.validation)
  // transients required by jakarta transaction which is required by hibernate
  runtimeOnly(libs.bundles.jakarta.transaction)

  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesAnnotationProcessor(platform(libs.immutables.bom))
  testFixturesAnnotationProcessor(libs.immutables.core)

  testFixturesRuntimeOnly(libs.spring.data.jpa)

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
        implementation(sb.spring.test)
        implementation(sb.spring.boot.test.autoconfigure)
        implementation(sb.spring.boot.test)

        runtimeOnly(sb.h2)
        runtimeOnly(sb.spring.boot.starter.validation.test)
        runtimeOnly(sb.spring.boot.starter.data.jpa.test)
        runtimeOnly(sb.spring.boot.starter.aspectj.test)
        runtimeOnly(sb.spring.data.envers)
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sb.spring.beans)
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

        runtimeOnly(sb.hibernate.core)
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
