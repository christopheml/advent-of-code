plugins {
  alias(libs.plugins.kotlin.jvm)
  `java-test-fixtures`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.arrow.core)
  implementation(libs.bundles.ktor.client)
  testImplementation(libs.bundles.test)
  testImplementation(kotlin("test"))
  testFixturesImplementation(libs.bundles.test)
}

kotlin {
  jvmToolchain(libs.versions.jdk.get().toInt())
}

tasks.test {
  useJUnitPlatform()
}
