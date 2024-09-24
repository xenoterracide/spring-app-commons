// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

tasks.javadoc {
  enabled = false
}

dependencies {
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.context)

  runtimeOnly(libs.spring.test)

  testImplementation(libs.spring.boot.test.core)

  testCompileOnly(libs.spring.test)
}
