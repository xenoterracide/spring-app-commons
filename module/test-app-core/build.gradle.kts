// Copyright 2023 - 2025 Caleb Cushing
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
