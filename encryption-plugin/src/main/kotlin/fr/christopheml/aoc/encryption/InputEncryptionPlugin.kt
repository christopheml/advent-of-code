/*
 * MIT License
 *
 * Copyright (c) 2015-2025 Christophe MICHEL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
