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
  implementation(libs.java.tools)
  runtimeOnly(sbd4.spring.boot.starter.data.jpa)
  plantuml(libs.plantuml)
  testFixturesCompileOnly(sbd4.jakarta.annotation.api)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        runtimeOnly(sbd4.h2)
        runtimeOnly(projects.testAppCore)
        implementation(sbd4.spring.beans)
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

dependencies {
  runtimeOnly(sbd4.spring.boot.starter.log4j2)

  modules {
    module("org.springframework.boot:spring-boot-starter-logging") {
      replacedBy(
        "org.springframework.boot:spring-boot-starter-log4j2",
        "Use Log4j2 instead of Logback",
      )
    }
  }
}
