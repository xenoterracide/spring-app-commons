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
  implementation(libs.spring.security.oauth2.authorization.server)
  implementation(libs.spring.security.oauth2.core)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.oauth2.authorization.server)
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.spring.boot.devtools)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.bundles.test.runtime)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.test.impl)
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.starter.web)
  testImplementation(libs.starter.webflux)
}
