// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.spring.bom))
  annotationProcessor(platform(libs.immutables.bom))
  annotationProcessor(libs.immutables.core)

  compileOnly(platform(libs.immutables.bom))
  compileOnly(libs.bundles.immutables)
  compileOnly(libs.jspecify)
  compileOnly(libs.jakarta.annotation)

  api(platform(libs.spring.bom))
  api(projects.jpa)
  api(libs.jakarta.persistence)
  api(libs.jakarta.validation)
  api(libs.hibernate.envers)
  api(libs.spring.data.commons)
  api(libs.spring.data.jpa)

  implementation(libs.uuid.creator)

  testFixturesAnnotationProcessor(platform(libs.immutables.bom))
  testFixturesAnnotationProcessor(libs.immutables.core)

  testFixturesCompileOnly(platform(libs.immutables.bom))
  testFixturesCompileOnly(libs.bundles.immutables)
  testFixturesCompileOnly(libs.jspecify)

  testFixturesImplementation(libs.uuid.creator)

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
