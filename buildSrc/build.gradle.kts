// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `kotlin-dsl`
}

dependencyLocking { lockAllConfigurations() }

dependencies {
  implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
