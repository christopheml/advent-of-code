/*
 * MIT License
 *
 * Copyright (c) 2025 Christophe MICHEL
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

package fr.christopheml.aoc2025.day03

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.MultiLineSolution
import fr.christopheml.aoc.common.toDigits

class Batteries : MultiLineSolution<Long>(day = 3) {

  override fun partOne(input: Input.MultiLine) = input
    .map { it.toDigits() }
    .sumOf { it.toJoltage() }

  override fun partTwo(input: Input.MultiLine) = input
    .map { it.toDigits() }
    .sumOf { it.toMaxJoltage() }

  private fun List<Int>.toJoltage(): Long {
    val (firstDigit, others) = findDigit(this, 1)
    val secondDigit = others.max()
    return firstDigit * 10L + secondDigit
  }

  private fun List<Int>.toMaxJoltage(): Long = buildList {
    var current = this@toMaxJoltage
    for (remaining in 11 downTo 0) {
      val (digit, others) = findDigit(current, remaining)
      add(digit)
      current = others
    }
  }.fold(0L) { acc, digit -> acc * 10L + digit }

  private fun findDigit(digits: List<Int>, remaining: Int): Pair<Int, List<Int>> {
    val digit = digits.dropLast(remaining).max()
    return Pair(digit, digits.drop(digits.indexOf(digit) + 1))
  }

}

fun main() {
  Batteries().run()
}
