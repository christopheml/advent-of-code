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

import fr.christopheml.aoc.encryption.worker.DecryptSingleFile
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.SkipWhenEmpty
import org.gradle.api.tasks.TaskAction
import org.gradle.workers.WorkerExecutor
import java.io.File
import javax.inject.Inject


abstract class InputDecryptionTask : DefaultTask() {

  @get:InputFiles
  abstract val keyFile: Property<File>

  @get:Input
  abstract val includes: Property<String>

  @get:InputFiles
  @get:SkipWhenEmpty
  val encryptedFiles: FileCollection by lazy {
    project.files(
      project.fileTree("src/main/resources") {
        this.include(this@InputDecryptionTask.includes.get() + extension)
      }.files
    )
  }

  @get:OutputFiles
  val resourceFiles: FileCollection by lazy {
    encryptedFiles
      .map { project.file(it.toDecryptedName()) }
      .let { project.files(it) }
  }

  @Inject
  abstract fun getWorkerExecutor(): WorkerExecutor

  @TaskAction
  open fun decrypt() {
    val workQueue = getWorkerExecutor().noIsolation()

    encryptedFiles.forEach { input ->
      val output = input.toDecryptedName()
      if (!output.exists()) {
        workQueue.submit(DecryptSingleFile::class.java) {
          getSourceFile().set(input)
          getTransformedFile().set(output)
          getKeyFile().set(keyFile.get())
        }
      }
    }
  }

  private fun File.toDecryptedName() = File(this.path.removeSuffix(extension))

}
