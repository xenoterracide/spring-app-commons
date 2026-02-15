// SPDX-FileCopyrightText: Copyright © 2023 - 2026 Caleb Cushing
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
  implementation(libs.plugin.dependency.analysis)
  implementation(libs.plugin.errorprone)
  runtimeOnly(libs.plugin.convention.checkstyle)
  runtimeOnly(libs.plugin.convention.compile)
  runtimeOnly(libs.plugin.convention.coverage)
  runtimeOnly(libs.plugin.convention.javadoc)
  runtimeOnly(libs.plugin.convention.spotbugs)
  runtimeOnly(libs.plugin.convention.test)
}
