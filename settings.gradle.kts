rootProject.name = "advent-of-code"

include("common")
include("2015")
include("2025")

includeBuild("encryption-plugin")

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
