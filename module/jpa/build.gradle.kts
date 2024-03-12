// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  compileOnly(libs.jspecify)

  api(platform(libs.spring.bom))
  api(libs.jakarta.validation)
  api(libs.jakarta.persistence)

  implementation(platform(libs.spring.bom))
  implementation(libs.uuid.creator)
  implementation(libs.hibernate.orm.core)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.h2)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.starter.validation)
  testImplementation(libs.starter.data.jpa)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(projects.testApp)
}

tasks.compileJava {
  options.release = 17
}
