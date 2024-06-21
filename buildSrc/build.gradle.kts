// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `kotlin-dsl`
}

dependencyLocking { lockAllConfigurations() }

dependencies {
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
  implementation(libs.plugin.spotless)
  implementation(libs.plugin.spotbugs)
  implementation(libs.plugin.errorprone)
  implementation(libs.plugin.semver)
  implementation(libs.plugin.gradlex.module.testing)
}
