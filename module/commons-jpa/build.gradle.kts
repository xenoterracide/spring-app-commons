// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(libs.hibernate.jpa.modelgen)
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  api(sbd3.jakarta.persistence.api)
  api(sbd3.jakarta.validation.api)
  api(libs.jmolecules.ddd)
  api(projects.commonsModel)
  api(sbd3.hibernate.envers)
  api(sbd3.spring.context)
  api(sbd3.spring.data.commons)
  compileOnly(libs.java.tools)
  compileOnly(sbd3.hibernate.validator)
  implementation(sbd3.commons.lang3)
  implementation(sbd3.spring.beans)
  implementation(sbd3.spring.tx)
  runtimeOnly(libs.bundles.jakarta.transaction)
  runtimeOnly(sbd3.spring.boot.starter.data.jpa)
  runtimeOnly(sbd3.spring.boot.starter.validation)
  testFixturesAnnotationProcessor(libs.hibernate.jpa.modelgen)
  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesApi(libs.jmolecules.ddd)
  testFixturesApi(projects.commonsModel)
  testFixturesApi(sbd3.hibernate.envers)
  testFixturesApi(sbd3.jakarta.persistence.api)
  testFixturesApi(sbd3.jakarta.validation.api)
  testFixturesApi(sbd3.spring.data.commons)
  testFixturesApi(sbd3.spring.data.jpa)
  testFixturesCompileOnlyApi(libs.jspecify)
  testFixturesImplementation(libs.java.tools)
  testFixturesImplementation(libs.uuid.creator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sbd3.spring.orm)
      }
    }
    val testWhitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(projects.commonsModel)
        implementation(libs.equalsverifier)
        implementation(sbd3.commons.lang3)
        implementation(sbd3.spring.beans)
        implementation(sbd3.spring.tx)
        implementation(sbd3.hibernate.core)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))

        implementation(platform(libs.jakarta.bom))
        implementation(sbd3.spring.test)
        implementation(sbd3.spring.boot.test.autoconfigure)
        implementation(sbd3.spring.boot.test)
        implementation.bundle(sbd3.bundles.test.impl)

        runtimeOnly(sbd3.h2)
        runtimeOnly(sbd3.spring.boot.starter.validation)
        runtimeOnly(sbd3.spring.boot.starter.data.jpa)
        runtimeOnly(sbd3.spring.boot.starter.aop)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sbd3.spring.data.envers)
        runtimeOnly.bundle(sbd3.bundles.test.runtime)
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

dependencies {
  runtimeOnly(sbd3.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
