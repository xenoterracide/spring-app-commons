// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

buildscript { dependencyLocking { lockAllConfigurations() } }

plugins {
  `java-library`
  our.publish
}

tasks.javadoc {
  enabled = false
}
