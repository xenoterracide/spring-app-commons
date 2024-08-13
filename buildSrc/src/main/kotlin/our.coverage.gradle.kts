// © Copyright 2023-2024 Caleb Cushing
// SPDX-License-Identifier: MIT

plugins {
  `java-base`
  jacoco
}

val coverage = project.extensions.create<CoveragePluginExtension>("coverage")

tasks.withType<JacocoReport>().configureEach {
  dependsOn(project.tasks.withType<Test>())
  // execution data needs to be aggregated from all exec files in the project for multi jvm test suite testing
  executionData(project.layout.buildDirectory.dir("jacoco").map { it.asFileTree.toList() })
}

project.tasks.check.configure {
  dependsOn(tasks.withType<JacocoCoverageVerification>())
}

tasks.withType<JacocoCoverageVerification>().configureEach {
  // execution data needs to be aggregated from all exec files in the project for multi jvm test suite testing
  executionData(project.tasks.withType<JacocoReport>().map { it.executionData })
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
