// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

tasks.compileJava {
  options.release = 17
}

tasks.javadoc {
  enabled = false
}

dependencies {
  implementation(platform(libs.spring.bom))
  implementation(libs.spring.boot.autoconfigure)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.log4j2)
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.starter.oauth2.client)
}
