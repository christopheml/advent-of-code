package fr.christopheml.aoc.encryption

import java.io.File
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import kotlin.io.encoding.Base64

class EncryptionKey internal constructor(val underlying: SecretKey) {

  fun writeToFile(file: File) {
    file.writeText(Base64.encode(underlying.encoded))
  }

  companion object {

    fun generate(): EncryptionKey {
      val keyGenerator = KeyGenerator.getInstance(algorithm)
      keyGenerator.init(keySize)
      return EncryptionKey(keyGenerator.generateKey())
    }

    fun readFromFile(file: File): EncryptionKey {
      val bytes = Base64.decode(file.readText())
      return EncryptionKey(SecretKeySpec(bytes, algorithm))
    }

  }

}
