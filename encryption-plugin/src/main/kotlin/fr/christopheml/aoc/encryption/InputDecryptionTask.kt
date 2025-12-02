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
