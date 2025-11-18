group = "fr.christopheml"

repositories {
  mavenCentral()
}

plugins {
  alias(libs.plugins.kotlin.jvm)
}

dependencies {
  testImplementation(kotlin("test"))
}

tasks.test {
  useJUnitPlatform()
}
