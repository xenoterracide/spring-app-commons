// SPDX-FileCopyrightText: Copyright © 2023 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `kotlin-dsl`
}

dependencyLocking { lockAllConfigurations() }

dependencies {
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
  implementation(libs.plugin.convention.publish)
  implementation(libs.plugin.spotbugs)
  implementation(libs.plugin.errorprone)
  implementation(libs.plugin.semver)

  runtimeOnly(libs.plugin.convention.checkstyle)
  runtimeOnly(libs.plugin.convention.coverage)
}
