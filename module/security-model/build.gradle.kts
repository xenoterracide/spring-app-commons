// Copyright 2023 - 2025 Caleb Cushing
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
  api(projects.commonsModel)
  api(projects.commonsJpa)

  implementation(libs.java.tools)

  plantuml(libs.plantuml)
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      dependencies {
        runtimeOnly(projects.testAppCore)
      }
    }
    val whitebox by getting(JvmTestSuite::class) {
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
