// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

plugins {
  our.javalibrary
}

tasks.withType<Test> {
  enabled = false
}

tasks.withType<JacocoCoverageVerification> {
  enabled = false
}

dependencies {
  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.starter.oauth2.client)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.starter.web)
  testImplementation(libs.starter.webflux)
  testImplementation(libs.spring.security.core)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(projects.testApp)
}
