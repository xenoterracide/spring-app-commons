// SPDX-FileCopyrightText: Copyright © 2023 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  annotationProcessor(libs.hibernate.jpa.modelgen)
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  api(libs.hibernate.envers)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.jmolecules.ddd)
  api(libs.spring.context)
  api(libs.spring.data.commons)
  api(projects.commonsModel)
  compileOnly(libs.hibernate.validator)
  compileOnly(libs.java.tools)
  implementation(libs.commons.lang)
  implementation(libs.spring.beans)
  implementation(libs.spring.transaction)
  runtimeOnly(libs.bundles.jakarta.transaction)
  runtimeOnly(libs.starter.data.jpa)
  runtimeOnly(libs.starter.validation)
  testFixturesAnnotationProcessor(libs.hibernate.jpa.modelgen)
  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesApi(libs.hibernate.envers)
  testFixturesApi(libs.jakarta.persistence)
  testFixturesApi(libs.jakarta.validation)
  testFixturesApi(libs.jmolecules.ddd)
  testFixturesApi(libs.spring.data.commons)
  testFixturesApi(libs.spring.data.jpa)
  testFixturesApi(projects.commonsModel)
  testFixturesCompileOnlyApi(libs.jspecify)
  testFixturesImplementation(libs.java.tools)
  testFixturesImplementation(libs.uuid.creator)
}

testing {
  suites {
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))

        implementation(platform(libs.jakarta.bom))
        implementation(libs.spring.test)
        implementation(libs.spring.boot.test.autoconfigure)
        implementation(libs.spring.boot.test.core)

        runtimeOnly(libs.h2)
        runtimeOnly(libs.starter.validation)
        runtimeOnly(libs.starter.data.jpa)
        runtimeOnly(libs.starter.aop)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(libs.spring.data.envers)
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.orm)
      }
    }
    val whitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(projects.commonsModel)
        implementation(libs.equalsverifier)
        implementation(libs.commons.lang)
        implementation(libs.spring.beans)
        implementation(libs.spring.transaction)
        implementation(libs.hibernate.orm.core)
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
