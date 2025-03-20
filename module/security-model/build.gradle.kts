// SPDX-FileCopyrightText: Copyright © 2023 - 2025 Caleb Cushing
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
    val diagrams = project.layout.projectDirectory.dir("diagrams")
    name("Security Model")
    include(classes().withNameLike("com.xenoterracide.*"))
    exclude(fields().thatDontHaveAccessors())
    writeTo(diagrams.file("class.puml"))
    renderTo(diagrams.file("class.svg"))
  }
}
