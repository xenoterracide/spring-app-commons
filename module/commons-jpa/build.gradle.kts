// SPDX-FileCopyrightText: Copyright © 2023 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.jakarta.bom))
  testFixturesAnnotationProcessor(platform(libs.helidon.dependencies))
  testFixturesAnnotationProcessor(libs.helidon.bundles.apt)
  testFixturesAnnotationProcessor(libs.helidon.data.jakarta.persistence.codegen)
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(libs.hibernate.validator)
  compileOnly(libs.java.tools)

  api(projects.commonsModel)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.jmolecules.ddd)

  api(platform(libs.helidon.dependencies))
  implementation(libs.commons.lang)
  testImplementation(libs.log4j.api)
  testRuntimeOnly(libs.helidon.logging.log4j)
  testRuntimeOnly(libs.hibernate.orm.core)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.helidon.config.yaml)
  testImplementation(libs.helidon.config)
  testFixturesApi(libs.helidon.data)
  testFixturesApi(libs.helidon.data.jakarta.persistence)

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
