package fr.christopheml.aoc.encryption

import fr.christopheml.aoc.encryption.worker.DecryptSingleFile
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.work.ChangeType
import org.gradle.work.InputChanges
import org.gradle.workers.WorkerExecutor
import java.io.File
import javax.inject.Inject


abstract class ResourceDecryptionTask : DefaultTask() {

  @get:InputFiles
  abstract val keyFile: Property<File>

  @get:Input
  abstract val includes: Property<String>

  @get:InputFiles
  @get:SkipWhenEmpty
  val encryptedFiles: FileCollection by lazy {
    project.files(
      project.fileTree("src/main/resources") {
        this.include(this@ResourceDecryptionTask.includes.get() + extension)
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
  open fun decrypt(inputChanges: InputChanges) {
    val workQueue = getWorkerExecutor().noIsolation()

    inputChanges.getFileChanges(encryptedFiles).forEach { change ->
      when (change.changeType) {
        ChangeType.ADDED,
        ChangeType.MODIFIED -> {
          workQueue.submit(DecryptSingleFile::class.java) {
            getSourceFile().set(change.file)
            getTransformedFile().set(change.file.toDecryptedName())
            getKeyFile().set(keyFile.get())
          }
        }

        ChangeType.REMOVED -> {
          /* do nothing */
        }
      }
    }
  }

  private fun File.toDecryptedName() = File(this.path.removeSuffix(extension))

}
