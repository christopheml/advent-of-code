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
