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
  implementation(libs.spring.context)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.spring.test)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.spring.beans)
  testImplementation(libs.spring.boot.test.core)

  testCompileOnly(platform(libs.spring.bom))
  testCompileOnly(libs.spring.test)
}
