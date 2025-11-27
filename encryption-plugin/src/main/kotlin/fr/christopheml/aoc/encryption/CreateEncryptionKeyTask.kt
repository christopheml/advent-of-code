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
