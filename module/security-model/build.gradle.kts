// SPDX-FileCopyrightText: Copyright © 2023 - 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
  our.jpamodel
  alias(libs.plugins.plantuml)
}

val plantuml by configurations.creating

dependencies {
  annotationProcessor(platform(libs.helidon.dependencies))
  annotationProcessor(libs.helidon.bundles.apt)
  annotationProcessor(libs.helidon.data.jakarta.persistence.codegen)

  api(projects.commonsModel)
  api(projects.commonsJpa)
  api(platform(libs.helidon.dependencies))
  implementation(libs.helidon.data)
  implementation(libs.helidon.data.jakarta.persistence)
  implementation(libs.java.tools)
  runtimeOnly(libs.bundles.helidon.data.jpa.runtime)

  plantuml(libs.plantuml)
}

testing {
  suites {
    withType<JvmTestSuite>().configureEach {
      dependencies {
        implementation(testFixtures(project()))

        implementation(platform(libs.jakarta.bom))

        runtimeOnly(libs.h2)
        runtimeOnly(libs.helidon.logging.log4j)
        implementation(libs.log4j.api)
        runtimeOnly(libs.helidon.data.sql.datasource.hikari)
        runtimeOnly(libs.helidon.config.yaml)
        implementation(libs.helidon.config)
      }
    }

    val test by getting(JvmTestSuite::class) {
      dependencies {
        runtimeOnly(libs.helidon.service.registry)
        runtimeOnly(libs.hibernate.orm.core)
      }
    }
    val testWhitebox by getting(JvmTestSuite::class) {
      dependencies {
        implementation(project())
        implementation(libs.hibernate.orm.core)
        implementation(libs.helidon.transaction.narayana)
        implementation(libs.jakarta.transaction)
        implementation(projects.commonsModel)
        implementation(libs.equalsverifier)
        implementation(libs.commons.lang)
      }
    }
  }
}

classDiagrams {
  renderClasspath(plantuml)
  diagram {
    name("Security Model")
    include(classes().insideOfProject())
    exclude(fields().thatDontHaveAccessors())
    writeTo(project.layout.files("diagrams/class.puml").single())
    renderTo(project.layout.files("diagrams/class.svg").single())
  }
}
