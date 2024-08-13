// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

plugins {
  jacoco
  `java-base`
}

val coverage = project.extensions.create<CoveragePluginExtension>("coverage")

tasks.withType<JacocoReport>().configureEach {
  dependsOn(project.tasks.withType<Test>())
}

project.tasks.check.configure {
  dependsOn(tasks.withType<JacocoCoverageVerification>())
}

tasks.withType<JacocoCoverageVerification>().configureEach {
  dependsOn(project.tasks.withType<JacocoReport>())
  violationRules {
    rule {
      limit {
        coverage.minimum.convention(0.9).let { minimum = it.get().toBigDecimal() }
      }
    }
  }
}

interface CoveragePluginExtension {
  val minimum: Property<Double>
}
