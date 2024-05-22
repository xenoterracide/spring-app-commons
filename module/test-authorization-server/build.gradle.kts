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
  implementation(libs.spring.beans)
  implementation(libs.spring.boot.core)
  implementation(libs.spring.context)
  implementation(libs.spring.security.oauth2.authorization.server)
  implementation(libs.spring.security.oauth2.core)

  compileOnly(libs.java.tools)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.oauth2.authorization.server)
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.spring.boot.devtools)

  testFixturesImplementation(platform(libs.spring.bom))
  testFixturesImplementation(libs.spring.web)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.test.impl)
  testImplementation(libs.bundles.spring.test)
  testImplementation(libs.spring.web)
  testImplementation(libs.httpcomponents.client5)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.bundles.test.runtime)
  testRuntimeOnly(projects.config)
}

tasks.withType<Test>().configureEach {
  systemProperty(
    "jdk.httpclient.HttpClient.log",
    "frames,all",
  )
}
