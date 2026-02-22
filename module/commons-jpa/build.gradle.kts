// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(libs.hibernate.jpa.modelgen)
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  api(sbd4.jakarta.persistence.api)
  api(sbd4.jakarta.validation.api)
  api(libs.jmolecules.ddd)
  api(projects.commonsModel)
  api(sbd4.hibernate.envers)
  api(sbd4.spring.context)
  api(sbd4.spring.data.commons)
  compileOnly(libs.java.tools)
  compileOnly(sbd4.hibernate.validator)
  implementation(sbd4.commons.lang3)
  implementation(sbd4.spring.beans)
  implementation(sbd4.spring.tx)
  runtimeOnly(libs.bundles.jakarta.transaction)
  runtimeOnly(sbd4.spring.boot.starter.data.jpa)
  runtimeOnly(sbd4.spring.boot.starter.validation)
  testFixturesAnnotationProcessor(libs.hibernate.jpa.modelgen)
  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesApi(libs.jmolecules.ddd)
  testFixturesApi(projects.commonsModel)
  testFixturesApi(sbd4.hibernate.envers)
  testFixturesApi(sbd4.jakarta.persistence.api)
  testFixturesApi(sbd4.jakarta.validation.api)
  testFixturesApi(sbd4.spring.data.commons)
  testFixturesApi(sbd4.spring.data.jpa)
  testFixturesCompileOnlyApi(sbd4.jspecify)
  testFixturesImplementation(libs.java.tools)
  testFixturesImplementation(libs.uuid.creator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd4.spring.orm)
      }
    }
    val testWhitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(projects.commonsModel)
        implementation(libs.equalsverifier)
        implementation(sbd4.commons.lang3)
        implementation(sbd4.spring.beans)
        implementation(sbd4.spring.tx)
        implementation(sbd4.hibernate.core)
        implementation(sbd4.spring.boot.jdbc.test)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(platform(libs.jakarta.bom))
        implementation(sbd4.spring.boot.data.jpa.test)
        implementation(sbd4.spring.boot.test)
        implementation(sbd4.spring.test)
        implementation(testFixtures(project()))
        implementation.bundle(sbd4.bundles.test.impl)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sbd4.h2)
        runtimeOnly(sbd4.spring.boot.starter.aspectj)
        runtimeOnly(sbd4.spring.boot.starter.data.jpa)
        runtimeOnly(sbd4.spring.boot.starter.validation)
        runtimeOnly(sbd4.spring.data.envers)
        runtimeOnly.bundle(sbd4.bundles.test.runtime)
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

tasks.compileTestFixturesJava {
  options.compilerArgs.addAll(jpaModelGen)
}
