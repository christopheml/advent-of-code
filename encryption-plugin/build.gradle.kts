plugins {
  `kotlin-dsl`
}

group = "fr.christopheml.aoc"

repositories {
  mavenCentral()
}

gradlePlugin {
  val resourceEncryption by plugins.creating {
    id = "fr.christopheml.aoc.resource-encryption"
    implementationClass = "fr.christopheml.aoc.encryption.EncryptionPlugin"
  }
}
