package fr.christopheml.aoc.encryption.worker

import fr.christopheml.aoc.encryption.EncryptionKey
import fr.christopheml.aoc.encryption.transformation
import org.gradle.workers.WorkAction
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import kotlin.io.encoding.Base64

abstract class DecryptSingleFile : WorkAction<SingleFileParameters> {

  override fun execute() {
    val keyFile = parameters.getKeyFile().asFile.get()
    val source = parameters.getSourceFile().asFile.get()
    val destination = parameters.getTransformedFile().asFile.get()
    val key = EncryptionKey.readFromFile(keyFile)
    val cipher = Cipher.getInstance(transformation)

    val (iv, encrypted) = readEncryptedFile(source)
    val ivParameterSpec = IvParameterSpec(iv)
    cipher.init(Cipher.DECRYPT_MODE, key.underlying, ivParameterSpec)
    val decrypted = cipher.doFinal(encrypted)
    write(decrypted, destination)
  }

  private fun readEncryptedFile(source: File): List<ByteArray> =
    source.readLines()
      .take(2)
      .map { Base64.decode(it) }


  private fun write(encrypted: ByteArray, destination: File) {
    destination.printWriter().use {
      it.print(encrypted.toString(Charsets.UTF_8))
    }
  }

}
