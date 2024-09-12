// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(platform(libs.immutables.bom))
  annotationProcessor(libs.immutables.core)
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(platform(libs.immutables.bom))
  compileOnly(libs.bundles.immutables)
  compileOnly(libs.java.tools)

  api(projects.jpa)
  api(projects.model)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.hibernate.envers)
  api(libs.spring.data.jpa)

  implementation(libs.uuid.creator)

  runtimeOnly(libs.starter.data.jpa)
  runtimeOnly(libs.starter.aop)
  runtimeOnly(libs.starter.validation)

  testFixturesAnnotationProcessor(platform(libs.immutables.bom))
  testFixturesAnnotationProcessor(libs.immutables.core)

  testFixturesCompileOnly(platform(libs.immutables.bom))
  testFixturesCompileOnly(libs.bundles.immutables)
  testFixturesCompileOnly(libs.jspecify)

  testFixturesImplementation(libs.uuid.creator)
  testFixturesImplementation(libs.jakarta.annotation)
}

testing {
  suites {
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.beans)
        implementation(libs.spring.boot.test.autoconfigure)
        implementation(libs.spring.test)

        runtimeOnly(projects.testApp)
        runtimeOnly(libs.h2)
      }
    }
    val whitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(libs.equalsverifier)
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
