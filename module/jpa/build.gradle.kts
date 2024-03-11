// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  compileOnly(libs.jspecify)

  implementation(platform(libs.spring.bom))
  implementation(libs.jakarta.validation)
  implementation(libs.jakarta.persistence)
  implementation(libs.uuid.creator)
  implementation(libs.hibernate.orm.core)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.starter.validation)
  testImplementation(libs.starter.data.jpa)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(projects.testApp)
}

tasks.compileJava {
  options.release = 17
}
