// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins { our.javalibrary }

dependencies {
  annotationProcessor(platform(libs.spring.bom))

  compileOnly(libs.jspecify)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.junit.api)
  testImplementation(libs.equalsverifier)
}

tasks.compileJava {
  options.release = 17
  options.compilerArgs.removeIf { it == "-Werror" } // either that or remove specific jpa modelgen warnings
}
