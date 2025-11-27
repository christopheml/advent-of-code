plugins {
  alias(libs.plugins.kotlin.jvm)
  id("fr.christopheml.aoc.resource-encryption")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(project(":common"))
  implementation(libs.arrow.core)
  testImplementation(libs.bundles.test)
  testImplementation(kotlin("test"))
}

kotlin {
  jvmToolchain(libs.versions.jdk.get().toInt())
}

tasks.test {
  useJUnitPlatform()
}

inputEncryption {
  includes = "inputs/*.txt"
}
