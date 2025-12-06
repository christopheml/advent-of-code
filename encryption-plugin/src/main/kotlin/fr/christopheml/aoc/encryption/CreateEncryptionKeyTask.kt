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

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import java.io.File
import javax.inject.Inject

abstract class CreateEncryptionKeyTask @Inject constructor(private val execOperations: ExecOperations) : DefaultTask() {

  @get:Input
  abstract val keyFilename: Property<String>

  @get:OutputFile
  val keyFile: Provider<File> = keyFilename.map { project.rootProject.file(it) }

  @TaskAction
  open fun generateKey() {
    if (keyFileNotIgnoredByGit()) {
      throw GradleException("Key file ${actualKeyFile().path} is not ignored by .gitignore")
    }

    if (actualKeyFile().exists()) {
      logger.warn("Key file ${actualKeyFile().path} already exists and was not overwritten")
      return
    }

    val key = EncryptionKey.generate()
    key.writeToFile(actualKeyFile())
  }

  private fun keyFileNotIgnoredByGit() =
    execOperations.exec {
      isIgnoreExitValue = true
      commandLine("git", "check-ignore", "--quiet", "--no-index", actualKeyFile().path)
    }.exitValue != 0

  private fun actualKeyFile() = keyFile.get()

}
