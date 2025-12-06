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

package fr.christopheml.aoc2015.day04

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.SingleLineSolution
import java.security.MessageDigest
import kotlin.experimental.and

class AdventCoinMiner : SingleLineSolution<Int>(day = 4) {

  private val md5 = MessageDigest.getInstance("MD5")
  private val zero = 0.toByte()
  private val halfZero = 0xF0.toByte()

  override fun partOne(input: Input.SingleLine) = findMatchingMd5(input.value, ::hasFiveLeadingZeroes)

  override fun partTwo(input: Input.SingleLine) = findMatchingMd5(input.value, ::hasSixLeadingZeroes)

  private fun findMatchingMd5(root: String, predicate: (ByteArray) -> Boolean): Int =
    generateSequence(1) { it + 1 }
      .map { root + it }
      .map { md5.digest(it.encodeToByteArray()) }
      .indexOfFirst(predicate) + 1


  private fun hasFiveLeadingZeroes(bytes: ByteArray) =
    bytes[0] == zero && bytes[1] == zero && (bytes[2] and halfZero) == zero

  private fun hasSixLeadingZeroes(bytes: ByteArray) =
    bytes[0] == zero && bytes[1] == zero && bytes[2] == zero

}

fun main() {
  AdventCoinMiner().run()
}
