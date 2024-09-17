// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(libs.java.tools)

  api(projects.commonsModel)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.spring.context)
  api(libs.spring.data.commons)
  api(libs.hibernate.envers)
  api(libs.jmolecules.ddd)

  implementation(libs.commons.lang)
  implementation(libs.spring.beans)
  implementation(libs.spring.transaction)

  runtimeOnly(libs.starter.data.jpa)
  runtimeOnly(libs.starter.validation)
  // transients required by jakarta transaction which is required by hibernate
  runtimeOnly(libs.bundles.jakarta.transaction)

  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.spring.bom))
  testFixturesAnnotationProcessor(libs.hibernate.jpa.modelgen)

  testFixturesApi(projects.commonsModel)
  testFixturesApi(libs.spring.data.jpa)
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

tasks.compileJava {
  options.release = 17
  options.compilerArgs.addAll(
    listOf(
      "-AaddSuppressWarningsAnnotation=true",
      "-AaddGeneratedAnnotation=true",
    ),
  )
}
