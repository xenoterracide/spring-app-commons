// SPDX-FileCopyrightText: Copyright © 2023 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
  alias(libs.plugins.plantuml)
}

val plantuml by configurations.creating

dependencies {
  api(libs.spring.data.commons)
  api(libs.jmolecules.ddd)

  testImplementation(libs.jakarta.persistence)
  testImplementation(libs.java.tools)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.spring.beans)

  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(projects.testAppCore)

  // inexplicit transients
  testRuntimeOnly(libs.jakarta.cdi)
  testRuntimeOnly(libs.jakarta.lang.model)
  testRuntimeOnly(libs.jakarta.interceptor)
  testRuntimeOnly(libs.jakarta.transaction)

  plantuml(libs.plantuml)
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
