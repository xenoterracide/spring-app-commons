// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `java-library`
}

dependencies {
  implementation(platform(libs.spring.bom))
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.boot.core)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.spring.boot.devtools)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.starter.web)
  testImplementation(libs.starter.webflux)
  testImplementation(libs.spring.security.core)
}
