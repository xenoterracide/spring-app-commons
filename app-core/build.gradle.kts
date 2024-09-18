// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
  alias(libs.plugins.java.module.testing)
}

tasks.javadoc {
  enabled = false
}

dependencies {
  implementation(platform(libs.spring.modulith.bom))
  implementation(libs.spring.modulith.core)

  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.context)

  runtimeOnly(projects.securityController)
  runtimeOnly(libs.starter.actuator)

  testImplementation(platform(libs.jmolecules.bom))
  testImplementation(libs.jmolecules.archunit)
  testImplementation(libs.spring.test)
  testImplementation(libs.spring.boot.test.core)
  testRuntimeOnly(libs.h2)
}
