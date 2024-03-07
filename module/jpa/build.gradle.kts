// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking.lockAllConfigurations() }

plugins { our.javalibrary }

dependencies {
  compileOnly(libs.jspecify)
  implementation(platform(libs.spring.bom))
  implementation(libs.jakarta.validation)
  implementation(libs.jakarta.persistence)
  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.starter.validation)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
}

tasks.compileJava {
  options.release = 17
}
