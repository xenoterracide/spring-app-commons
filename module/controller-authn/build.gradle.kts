// © Copyright 2024 Caleb Cushing
// SPDX-License-Identifier: MIT

import com.github.spotbugs.snom.SpotBugsTask

plugins {
  our.javalibrary
}

val demoServer by sourceSets.creating

java {
  registerFeature("demoServer") {
    usingSourceSet(demoServer)
  }
}

val demoServerImplementation by configurations.existing
val demoServerRuntimeOnly by configurations.existing

dependencies {
  runtimeOnly(platform(libs.spring.bom))
  runtimeOnly(libs.starter.security)
  runtimeOnly(libs.starter.web)
  runtimeOnly(libs.starter.oauth2.resource.server)

  testFixturesImplementation(platform(libs.spring.bom))
  testFixturesImplementation(libs.log4j.api)
  testFixturesImplementation(libs.spring.security.core)
  testFixturesImplementation(libs.spring.web)

  testImplementation(platform(libs.spring.bom))
  testImplementation(libs.bundles.spring.test)

  testRuntimeOnly(platform(libs.spring.bom))
  testRuntimeOnly(libs.starter.web)
  testRuntimeOnly(libs.starter.webflux)
  // testRuntimeOnly(projects.testApp)

  demoServerRuntimeOnly(platform(libs.spring.bom))
  demoServerRuntimeOnly(testFixtures(project))
  demoServerRuntimeOnly(libs.spring.boot.devtools)

  demoServerImplementation(platform(libs.spring.bom))
  demoServerImplementation(libs.spring.boot.autoconfigure)
  demoServerImplementation(libs.spring.security.config)
  demoServerImplementation(libs.spring.security.core)
  demoServerImplementation(libs.spring.security.web)
}

tasks.withType<Test>().configureEach {
  enabled = false
}

tasks.withType<JacocoCoverageVerification>().configureEach {
  enabled = false
}

tasks.withType<SpotBugsTask>().configureEach {
  enabled = false
}

tasks.withType<Javadoc>().configureEach {
  enabled = false
}
