// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(libs.hibernate.validator)
  compileOnly(libs.java.tools)

  api(projects.commonsModel)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(sb.spring.context)
  api(sb.spring.data.commons)
  api(libs.hibernate.envers)
  api(libs.jmolecules.ddd)

  implementation(libs.commons.lang)
  implementation(sb.spring.beans)
  implementation(sb.spring.tx)

  runtimeOnly(sb.spring.boot.starter.data.jpa)
  runtimeOnly(sb.spring.boot.starter.validation)
  // transients required by jakarta transaction which is required by hibernate
  runtimeOnly(libs.bundles.jakarta.transaction)

  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesAnnotationProcessor(libs.hibernate.jpa.modelgen)

  testFixturesApi(projects.commonsModel)
  testFixturesApi(sb.spring.data.jpa)
  testFixturesApi(libs.hibernate.envers)
  testFixturesApi(libs.jakarta.persistence)
  testFixturesApi(libs.jakarta.validation)
  testFixturesApi(libs.jmolecules.ddd)
  testFixturesApi(sb.spring.data.commons)
  testFixturesImplementation(libs.uuid.creator)
  testFixturesImplementation(libs.java.tools)

  testFixturesCompileOnlyApi(libs.jspecify)
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

        runtimeOnly(libs.h2)
        runtimeOnly(sb.spring.boot.starter.validation)
        runtimeOnly(sb.spring.boot.starter.data.jpa)
        runtimeOnly(sb.spring.boot.starter.aop)
        runtimeOnly(projects.testAppCore)
        runtimeOnly(sb.spring.data.envers)
      }
    }

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
        implementation(libs.commons.lang)
        implementation(sb.spring.beans)
        implementation(sb.spring.tx)
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
