package fr.christopheml.aoc.encryption.worker

import org.gradle.api.file.RegularFileProperty
import org.gradle.workers.WorkParameters

interface SingleFileParameters : WorkParameters {
  fun getSourceFile(): RegularFileProperty
  fun getTransformedFile(): RegularFileProperty
  fun getKeyFile(): RegularFileProperty
}
