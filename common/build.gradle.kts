plugins {
  alias(libs.plugins.kotlin.jvm)
}

repositories {
  mavenCentral()
}

dependencies {
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
