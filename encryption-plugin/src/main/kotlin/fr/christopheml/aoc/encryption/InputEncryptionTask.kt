package fr.christopheml.aoc.encryption

import fr.christopheml.aoc.encryption.worker.EncryptSingleFile
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.work.ChangeType
import org.gradle.work.InputChanges
import org.gradle.workers.WorkerExecutor
import java.io.File
import javax.inject.Inject


abstract class InputEncryptionTask : DefaultTask() {

  @get:InputFiles
  abstract val keyFile: Property<File>

  @get:Input
  abstract val includes: Property<String>

  @get:InputFiles
  @get:SkipWhenEmpty
  val inputFiles: FileCollection by lazy {
    project.files(
      project.fileTree("src/main/resources") {
        this.include(this@InputEncryptionTask.includes.get())
      }.files
    )
  }

  @get:OutputFiles
  val encryptedInputFiles: FileCollection by lazy {
    inputFiles
      .map { project.file(it.toEncryptedName()) }
      .let { project.files(it) }
  }

  @Inject
  abstract fun getWorkerExecutor(): WorkerExecutor

  @TaskAction
  open fun encrypt(inputChanges: InputChanges) {
    val workQueue = getWorkerExecutor().noIsolation()

    inputChanges.getFileChanges(inputFiles).forEach { change ->
      when (change.changeType) {
        ChangeType.ADDED,
        ChangeType.MODIFIED -> {
          workQueue.submit(EncryptSingleFile::class.java) {
            getSourceFile().set(change.file)
            getTransformedFile().set(change.file.toEncryptedName())
            getKeyFile().set(keyFile.get())
          }
        }

        ChangeType.REMOVED -> {
          /* do nothing */
        }
      }
    }
  }

  private fun File.toEncryptedName() = File(this.path + ".encrypted")

}
