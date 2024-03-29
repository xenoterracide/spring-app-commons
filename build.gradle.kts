group = "com.xenoterracide"
version = "0.1.0-SNAPSHOT"

tasks.dependencies {
  dependsOn(subprojects.map { "${it.path}:dependencies" })
}
