// SPDX-FileCopyrightText: Copyright © 2023 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  annotationProcessor(platform(libs.helidon.dependencies))
  annotationProcessor(libs.helidon.bundles.apt)
  annotationProcessor(libs.helidon.data.jakarta.peristence.codegen)
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(libs.hibernate.validator)
  compileOnly(libs.java.tools)

  api(projects.commonsModel)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.jmolecules.ddd)

  implementation(platform(libs.helidon.dependencies))
  implementation(libs.commons.lang)
  implementation(libs.eclipselink.persistence.core)
  implementation(libs.eclipselink.persistence.jpa)
  implementation(libs.helidon.data)
  implementation(libs.helidon.data.jakarta.peristence)

  // transients required by jakarta transaction which is required by hibernate
  runtimeOnly(libs.bundles.jakarta.transaction)

  testFixturesAnnotationProcessor(platform(libs.jakarta.bom))

  testFixturesApi(projects.commonsModel)
  testFixturesApi(libs.jakarta.persistence)
  testFixturesApi(libs.jakarta.validation)
  testFixturesApi(libs.jmolecules.ddd)
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

        runtimeOnly(libs.h2)
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        implementation(libs.spring.orm)
      }
    }
    val testWhitebox by registering(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(projects.commonsModel)
        implementation(libs.equalsverifier)
        implementation(libs.commons.lang)
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
