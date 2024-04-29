// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `java-library`
}

dependencies {
  api(platform(libs.spring.bom))
  api(libs.spring.boot.autoconfigure)

  implementation(platform(libs.spring.bom))
  implementation(libs.spring.boot.core)
  implementation(libs.spring.context)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.spring.boot.devtools)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.starter.web)
  testImplementation(libs.starter.webflux)
}
