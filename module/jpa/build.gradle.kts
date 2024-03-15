// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  compileOnlyApi(libs.jspecify)

  api(platform(libs.spring.bom))
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)

  implementation(platform(libs.spring.bom))
  implementation(libs.commons.lang)
  implementation(libs.hibernate.envers)
  implementation(libs.spring.beans)
  implementation(libs.spring.context)
  implementation(libs.spring.transaction)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.starter.validation)
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(projects.testApp)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.spring.beans)
  testImplementation(libs.spring.data.jpa)
  testImplementation(libs.spring.data.envers)
  testImplementation(libs.spring.test)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.uuid.creator)
}

tasks.compileJava {
  options.release = 17
}
