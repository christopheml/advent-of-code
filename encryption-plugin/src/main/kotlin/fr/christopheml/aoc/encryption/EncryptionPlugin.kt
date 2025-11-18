package fr.christopheml.aoc.encryption

import org.gradle.api.Plugin
import org.gradle.api.Project

class EncryptionPlugin : Plugin<Project> {

  override fun apply(project: Project) {

    val extension = project.extensions.create(
      "resourceEncryption",
      ResourceEncryptionExtension::class.java
    ).apply {
      keyFilename.convention(".key")
      includes.convention("inputs/day*.txt")
    }

    project.tasks.register("createResourceEncryptionKey", CreateKeyTask::class.java) {
      group = "build"
      description = "Creates a new key for encrypting resources"

      keyFilename.set(extension.keyFilename)
    }

    project.tasks.register(
      "encryptResources",
      ResourceEncryptionTask::class.java
    ) {
      group = "build"
      description = "Encrypts resources for encryption"

      keyFile.set(extension.keyFilename.map { project.toRoot().file(it) })
      includes.set(extension.includes)
    }

    project.tasks.register(
      "decryptResources",
      ResourceDecryptionTask::class.java
    ) {
      group = "build"
      description = "Decrypts resources for use in projects"

      keyFile.set(extension.keyFilename.map { project.toRoot().file(it) })
      includes.set(extension.includes)
    }

  }

  private fun Project.toRoot(): Project {
    var root = this
    while (root.parent != null) {
      root = root.parent!!
    }
    return root
  }

}
