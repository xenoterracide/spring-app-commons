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
  api(projects.jpa)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.hibernate.envers)
  api(libs.spring.data.commons)
  api(libs.spring.data.jpa)

  implementation(platform(libs.spring.bom))
  implementation(libs.uuid.creator)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(projects.testApp)
  testRuntimeOnly(libs.h2)
  testRuntimeOnly(libs.starter.aop)
  testRuntimeOnly(libs.starter.data.jpa)
  testRuntimeOnly(libs.starter.validation)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
  testImplementation(libs.spring.boot.test.autoconfigure)
}

tasks.compileJava {
  options.release = 17
  options.compilerArgs.removeIf { it == "-Werror" } // either that or remove specific jpa modelgen warnings
}
