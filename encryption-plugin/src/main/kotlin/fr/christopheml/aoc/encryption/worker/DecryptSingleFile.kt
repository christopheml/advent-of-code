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
