// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(libs.java.tools)

  api(platform(libs.spring.bom))
  api(projects.model)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.spring.context)
  api(libs.spring.data.commons)
  api(libs.hibernate.envers)

  implementation(platform(libs.spring.bom))
  implementation(libs.commons.lang)
  implementation(libs.spring.beans)
  implementation(libs.spring.transaction)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.data.jpa)
  runtimeOnly(libs.starter.validation)

  testFixturesImplementation(platform(libs.spring.bom))
  testFixturesImplementation(libs.uuid.creator)
  testFixturesImplementation(libs.java.tools)
  testFixturesImplementation(libs.spring.data.jpa)

  testFixturesCompileOnlyApi(libs.jspecify)
}

testing {
  suites {
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))

        implementation(platform(libs.spring.bom))
        implementation(libs.spring.data.jpa)
        implementation(libs.spring.orm)
        implementation(libs.spring.test)
        implementation(libs.spring.boot.test.autoconfigure)
        implementation(libs.hibernate.orm.core)
        implementation(libs.spring.boot.test.core)

        runtimeOnly(platform(libs.spring.bom))
        runtimeOnly(libs.h2)
        runtimeOnly(libs.starter.validation)
        runtimeOnly(libs.starter.data.jpa)
        runtimeOnly(libs.starter.aop)
        runtimeOnly(projects.testApp)
        runtimeOnly(libs.spring.data.envers)
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        compileOnly(libs.jspecify)
        implementation(libs.equalsverifier)
      }
    }
    val whitebox by registering(JvmTestSuite::class) {
      dependencies {
      }
    }
  }
}

tasks.compileJava {
  options.release = 17
  options.compilerArgs.addAll(
    listOf(
      "-AaddSuppressWarningsAnnotation=true",
      "-AaddGeneratedAnnotation=true",
    ),
  )
}
