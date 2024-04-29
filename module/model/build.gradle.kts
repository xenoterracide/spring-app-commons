// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  api(platform(libs.spring.bom))
  api(libs.spring.data.commons)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.java.tools)
  testImplementation(libs.jakarta.persistence)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.spring.beans)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(projects.testApp)
}

tasks.compileJava {
  options.release = 17
}
