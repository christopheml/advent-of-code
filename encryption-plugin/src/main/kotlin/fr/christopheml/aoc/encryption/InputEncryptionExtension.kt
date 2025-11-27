package fr.christopheml.aoc.encryption

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

open class InputEncryptionExtension @Inject constructor(
  objects: ObjectFactory,
) {

  val keyFilename: Property<String> = objects.property(String::class.java)

  val includes: Property<String> = objects.property(String::class.java)

}

