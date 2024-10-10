// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(platform(libs.spring.bom))
  api(libs.spring.boot.autoconfigure)

  implementation(platform(libs.spring.bom))
  implementation(libs.spring.boot.core)
  implementation(libs.spring.context)
  implementation(libs.spring.security.oauth2.authorization.server)
  implementation(libs.spring.security.oauth2.core)
  implementation(libs.spring.core)
  implementation(libs.spring.security.config)
  implementation(libs.spring.security.web)
  implementation(libs.spring.web)

  compileOnly(libs.java.tools)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.oauth2.authorization.server)
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.spring.boot.devtools)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.test.impl)
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.httpcomponents.client5)
  testImplementation(libs.spring.beans)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.bundles.test.runtime)
  testRuntimeOnly(projects.testAppCore)
}
