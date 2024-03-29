// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.spring.bom))

  compileOnly(libs.jspecify)

  api(platform(libs.spring.bom))
  api(libs.spring.data.commons)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.h2)

  testImplementation(platform(libs.spring.bom))
  testImplementation(projects.testApp)
  testImplementation(libs.jakarta.persistence)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.junit.api)
}

tasks.compileJava {
  options.release = 17
  options.compilerArgs.removeIf { it == "-Werror" } // either that or remove specific jpa modelgen warnings
}
