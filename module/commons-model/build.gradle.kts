// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

dependencies {
  api(libs.spring.data.commons)
  api(libs.jmolecules.ddd)

  testImplementation(libs.jakarta.persistence)
  testImplementation(libs.java.tools)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.spring.beans)

  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(projects.testAppCore)

  // inexplicit transients
  testRuntimeOnly(libs.jakarta.cdi)
  testRuntimeOnly(libs.jakarta.lang.model)
  testRuntimeOnly(libs.jakarta.interceptor)
  testRuntimeOnly(libs.jakarta.transaction)
}
