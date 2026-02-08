// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(libs.hibernate.jpa.modelgen)
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  api(sb.jakarta.persistence.api)
  api(sb.jakarta.validation.api)
  api(libs.jmolecules.ddd)
  api(projects.commonsModel)
  api(sb.hibernate.envers)
  api(sb.spring.context)
  api(sb.spring.data.commons)
  compileOnly(libs.java.tools)
  compileOnly(sb.hibernate.validator)
  implementation(sb.commons.lang3)
  implementation(sb.spring.beans)
  implementation(sb.spring.tx)
  runtimeOnly(libs.bundles.jakarta.transaction)
  runtimeOnly(sb.spring.boot.starter.data.jpa)
  runtimeOnly(sb.spring.boot.starter.validation)
  testFixturesAnnotationProcessor(libs.hibernate.jpa.modelgen)
  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesApi(libs.jmolecules.ddd)
  testFixturesApi(projects.commonsModel)
  testFixturesApi(sb.hibernate.envers)
  testFixturesApi(sb.jakarta.persistence.api)
  testFixturesApi(sb.jakarta.validation.api)
  testFixturesApi(sb.spring.data.commons)
  testFixturesApi(sb.spring.data.jpa)
  testFixturesCompileOnlyApi(libs.jspecify)
  testFixturesImplementation(libs.java.tools)
  testFixturesImplementation(libs.uuid.creator)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(sb.spring.orm)
      }
    }
    val testWhitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(projects.commonsModel)
        implementation(libs.equalsverifier)
        implementation(sb.commons.lang3)
        implementation(sb.spring.beans)
        implementation(sb.spring.tx)
        implementation(sb.hibernate.core)
      }
    }
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))

        implementation(platform(libs.jakarta.bom))
        implementation(sb.spring.test)
        implementation(sb.spring.boot.test.autoconfigure)
        implementation(sb.spring.boot.test)
        implementation.bundle(sb.bundles.test.impl)

        runtimeOnly(sb.h2)
        runtimeOnly(sb.spring.boot.starter.validation)
        runtimeOnly(sb.spring.boot.starter.data.jpa)
        runtimeOnly(sb.spring.boot.starter.aop)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sb.spring.data.envers)
        runtimeOnly.bundle(sb.bundles.test.runtime)
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
  runtimeOnly(sb.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
