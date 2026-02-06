// © 2023, 2024 Copyright Caleb Cushing
// SPDX-FileCopyrightText: Copyright © 2026 Caleb Cushing
//
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  our.javalibrary
}

tasks.javadoc {
  enabled = false
}

dependencies {
  implementation(sb.spring.boot.autoconfigure)
  implementation(sb.spring.context)

  runtimeOnly(sb.spring.test)

  testImplementation(sb.spring.boot.test)

  testCompileOnly(sb.spring.test)
}
