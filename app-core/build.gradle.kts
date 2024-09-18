// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
  alias(libs.plugins.java.module.testing)
}

tasks.compileJava {
  options.release = 17
}

tasks.javadoc {
  enabled = false
}

dependencies {
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.context)

  testImplementation(platform(libs.spring.modulith.bom))
  testImplementation(libs.spring.modulith.core)
  testImplementation(libs.spring.test)
  testImplementation(libs.spring.boot.test.core)
}
