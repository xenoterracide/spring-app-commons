// SPDX-FileCopyrightText: Copyright © 2023-2026 Caleb Cushing
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
  api(projects.commonsJpa)
  api(projects.commonsModel)
  api(sbd4.hibernate.envers)
  api(sbd4.jakarta.persistence.api)
  api(sbd4.jakarta.validation.api)
  api(sbd4.spring.data.jpa)
  api(sbd4.jakarta.mail.api)
  api(platform(libs.axon.bom))
  implementation(libs.axon.eventsourcing)
  implementation(libs.axon.spring)
  implementation(libs.java.tools)
  runtimeOnly(libs.axon.spring.boot.autoconfigure) {
    exclude(group = "org.axonframework", module = "axon-server-connector")
  }
  runtimeOnly(sbd4.spring.boot.starter.data.jpa)
  plantuml(libs.plantuml)
  testFixturesImplementation(libs.axon.eventsourcing)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        runtimeOnly(sbd4.h2)
        runtimeOnly(projects.testAppCore)
        implementation.bundle(libs.bundles.axon.test)
        implementation(platform(libs.axon.bom))
        implementation(sbd4.spring.beans)
        implementation(sbd4.spring.boot.data.jpa.test)
        implementation(sbd4.spring.boot.test.autoconfigure)
        implementation(sbd4.spring.test)
      }
    }
    val testWhitebox by getting(JvmTestSuite::class) {
      dependencies {
        implementation(projects.commonsJpa)
        implementation(projects.commonsModel)
      }
    }
  }
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
  auxClassPaths.from(
    configurations.runtimeClasspath.map {
      it.filter { file ->
        file.name.contains("commons-lang3")
      }
    },
  )
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
