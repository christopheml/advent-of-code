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

package fr.christopheml.aoc2025.day06

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.math.multiply
import fr.christopheml.aoc.common.runner.MultiLineSolution

class CephalopodMath : MultiLineSolution<Long>(day = 6) {

  // TODO: Cleanup the implementation
  override fun partOne(input: Input.MultiLine) = input.map { line ->
    line.split("""\s+""".toRegex()).filter(String::isNotBlank)
  }.let {
    it[0].indices.map { i ->
      it.map { line -> line[i] }
    }.sumOf { problemInput -> problemInput.toProblem().solve() }
  }

  // TODO: Cleanup the implementation
  override fun partTwo(input: Input.MultiLine) =
    input[0].indices
      .reversed()
      .map { input.map { line -> line[it] }.filter { char -> !char.isWhitespace() } }
      .filter { it.isNotEmpty() }
      .let {
        val acc = mutableListOf<Long>()
        var result = 0L
        for (entry in it) {
          if (entry.last() == '*') {
            acc.add(entry.dropLast(1).toLong())
            result += acc.multiply()
            acc.clear()
          } else if (entry.last() == '+') {
            acc.add(entry.dropLast(1).toLong())
            result += acc.sum()
            acc.clear()
          } else { acc.add(entry.toLong()) }
        }
        result
      }
}

private data class Problem(
  val operands: List<Long>,
  val operation: Char,
) {
  fun solve(): Long = when (operation) {
    '*' -> operands.multiply()
    else -> operands.sum()
  }
}

private fun List<Char>.toLong() = fold(0L) { acc, next -> acc * 10 + next.digitToInt() }

private fun List<String>.toProblem() = Problem(
  operands = this.dropLast(1).map { it.toLong() },
  operation = this.last()[0],
)

fun main() {
  CephalopodMath().run()
}
