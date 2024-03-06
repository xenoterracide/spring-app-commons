// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking.lockAllConfigurations() }

plugins { our.javalibrary }

dependencies {
  compileOnlyApi(libs.jspecify)
  implementation(platform(libs.spring.bom))
  implementation(libs.jakarta.validation)
  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.starter.validation)
  testImplementation(libs.junit.api)
}

tasks.compileJava {
  options.release = 11
}
