package fr.christopheml.aoc.encryption

import org.gradle.api.Plugin
import org.gradle.api.Project

class InputEncryptionPlugin : Plugin<Project> {

  override fun apply(project: Project) {

    val extension = project.extensions.create(
      "inputEncryption",
      InputEncryptionExtension::class.java
    ).apply {
      keyFilename.convention(".key")
      includes.convention("inputs/day*.txt")
    }

    project.tasks.register("createEncryptionKey", CreateEncryptionKeyTask::class.java) {
      group = "advent-of-code"
      description = "Creates a new key for encrypting input files"

      keyFilename.set(extension.keyFilename)
    }

    project.tasks.register(
      "encryptInputs",
      InputEncryptionTask::class.java
    ) {
      group = "advent-of-code"
      description = "Encrypts inputs for sharing projects on the Internet"

      keyFile.set(extension.keyFilename.map { project.toRoot().file(it) })
      includes.set(extension.includes)
    }

    project.tasks.register(
      "decryptInputs",
      InputDecryptionTask::class.java
    ) {
      group = "advent-of-code"
      description = "Decrypts inputs for use in puzzles"

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
