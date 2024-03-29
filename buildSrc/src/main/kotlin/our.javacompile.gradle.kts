// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
  `java-library`
  `java-test-fixtures`
}

val libs = the<LibrariesForLibs>()

dependencies {
  compileOnly(platform(libs.spring.bom))
  compileOnly(libs.bundles.compile.annotations)
  testCompileOnly(libs.bundles.compile.annotations)
}

java {
  withJavadocJar()
  withSourcesJar()
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

/*
tasks.withType<Javadoc>().configureEach {
//  source(sourceSets.main.map { it.output.generatedSourcesDirs })
  (options as StandardJavadocDocletOptions).apply {
    addMultilineStringsOption("tag").value = listOf(
      "apiSpec:a:API Spec:",
      "apiNote:a:API Note:",
      "implSpec:a:Implementation Spec:",
      "implNote:a:Implementation Note:",
    )
  }
}

 */

tasks.withType<Jar> {
  archiveBaseName.set(project.path.substring(1).replace(":", "-"))
}

tasks.withType<JavaCompile>().configureEach {
  options.encoding = "UTF-8"
  options.compilerArgs.addAll(
    listOf(
      "-parameters",
      "-implicit:class",
      "-g",
      "-Xdiags:verbose",
      "-Xlint:all",
      "-Xlint:-processing",
      "-Xlint:-exports",
      "-Xlint:-requires-transitive-automatic",
      "-Xlint:-requires-automatic",
      "-Xlint:-fallthrough", // handled by error-prone in a smarter way
    ),
  )
}
