package fr.christopheml.aoc.encryption.worker

import fr.christopheml.aoc.encryption.EncryptionKey
import fr.christopheml.aoc.encryption.keySize
import fr.christopheml.aoc.encryption.transformation
import org.gradle.workers.WorkAction
import java.io.File
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import kotlin.io.encoding.Base64

abstract class EncryptSingleFile : WorkAction<SingleFileParameters> {

  private val random = SecureRandom()

  override fun execute() {
    val keyFile = parameters.getKeyFile().asFile.get()
    val source = parameters.getSourceFile().asFile.get()
    val destination = parameters.getTransformedFile().asFile.get()
    val key = EncryptionKey.readFromFile(keyFile)
    val cipher = Cipher.getInstance(transformation)
    val ivParameterSpec = generateIvParameterSpec()
    cipher.init(Cipher.ENCRYPT_MODE, key.underlying, ivParameterSpec)
    val encrypted = cipher.doFinal(source.readBytes())
    write(ivParameterSpec.iv, encrypted, destination)
  }

  private fun write(iv: ByteArray, encrypted: ByteArray, destination: File) {
    destination.printWriter().use {
      it.println(Base64.encode(iv))
      it.println(Base64.encode(encrypted))
    }
  }

  private fun generateIvParameterSpec(): IvParameterSpec {
    val iv = ByteArray(keySize / 8)
    random.nextBytes(iv)
    return IvParameterSpec(iv)
  }

}
