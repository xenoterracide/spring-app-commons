// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  compileOnly(libs.jspecify)

  api(platform(libs.spring.bom))
  api(libs.jakarta.validation)
  api(libs.jakarta.persistence)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.starter.validation)
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(projects.testApp)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.spring.beans)
  testImplementation(libs.spring.data.jpa)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.uuid.creator)
}

tasks.compileJava {
  options.release = 17
}
