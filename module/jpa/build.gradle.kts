// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnlyApi(libs.jspecify)

  api(platform(libs.spring.bom))
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.spring.context)

  implementation(platform(libs.spring.bom))
  implementation(libs.commons.lang)
  implementation(libs.spring.beans)
  implementation(libs.spring.transaction)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.starter.validation)
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.starter.aop)
  testRuntimeOnly(projects.testApp)
  testRuntimeOnly(libs.spring.data.envers)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.hibernate.envers)
  testImplementation(libs.spring.data.jpa)
  testImplementation(libs.spring.test)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.uuid.creator)
  testImplementation(libs.hibernate.orm.core)
  testImplementation(libs.spring.boot.test.core)
  testImplementation(libs.spring.data.commons)
}

tasks.compileJava {
  options.release = 17
  options.compilerArgs.removeIf { it == "-Werror" } // either that or remove specific jpa modelgen warnings
}
