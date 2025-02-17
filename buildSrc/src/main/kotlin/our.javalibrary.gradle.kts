// SPDX-FileCopyrightText: Copyright © 2023 - 2025 Caleb Cushing
//
// SPDX-License-Identifier: MIT

plugins {
  `java-library`
  id("our.bom")
  id("our.javacompile")
  id("our.javatest")
  id("our.spotbugs")
  id("com.xenoterracide.gradle.convention.checkstyle")
  id("com.xenoterracide.gradle.convention.coverage")
  id("our.publish")
}
