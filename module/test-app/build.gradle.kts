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
  compileOnly(platform(libs.spring.bom))
  compileOnly(libs.bundles.compile.annotations)

  implementation(platform(libs.spring.bom))
  implementation(libs.spring.boot.autoconfigure)
  implementation(libs.spring.test)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.log4j2)
  runtimeOnly(libs.spring.test)

  testCompileOnly(platform(libs.spring.bom))
  testCompileOnly(libs.spring.test)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.junit.api)
  testImplementation(libs.spring.beans)
  testImplementation(libs.spring.context)
  testImplementation(libs.spring.boot.test.core)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.bundles.junit.platform)
}
