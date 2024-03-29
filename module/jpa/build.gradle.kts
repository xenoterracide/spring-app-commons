// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(libs.hibernate.jpa.modelgen)

  compileOnly(libs.jspecify)
  compileOnly(libs.java.tools)

  api(platform(libs.spring.bom))
  api(projects.model)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.spring.context)
  api(libs.spring.data.commons)
  api(libs.hibernate.envers)

  implementation(platform(libs.spring.bom))
  implementation(libs.commons.lang)
  implementation(libs.spring.beans)
  implementation(libs.spring.transaction)

  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.data.jpa)
  runtimeOnly(libs.starter.validation)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.starter.validation)
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.starter.aop)
  testRuntimeOnly(projects.testApp)
  testRuntimeOnly(libs.spring.data.envers)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.spring.data.jpa)
  testImplementation(libs.spring.orm)
  testImplementation(libs.spring.test)
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(libs.spring.boot.test.autoconfigure)
  testImplementation(libs.uuid.creator)
  testImplementation(libs.hibernate.orm.core)
  testImplementation(libs.spring.boot.test.core)
  testImplementation(libs.java.tools)
}

tasks.compileJava {
  options.release = 17
  options.compilerArgs.removeIf { it == "-Werror" } // either that or remove specific jpa modelgen warnings
}
