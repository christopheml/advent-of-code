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

package fr.christopheml.aoc2025.day02

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.SingleLineSolution
import fr.christopheml.aoc.common.toLongRange
import kotlin.math.log10
import kotlin.math.pow

class ProductIds : SingleLineSolution<Long>(day = 2) {

  override fun partOne(input: Input.SingleLine): Long = input.checkIds(::partialCheck)

  override fun partTwo(input: Input.SingleLine): Long = input.checkIds(::fullCheck)

  private fun Input.SingleLine.checkIds(predicate: (Long) -> Boolean): Long =
    value.split(",")
      .map { it.toLongRange() }
      .flatMap { it.filter(predicate) }
      .sum()

  private fun partialCheck(id: Long): Boolean {
    val digits = log10(id.toDouble()).toInt() + 1
    val divided = 10.0.pow(digits / 2).toInt()
    return id > 10 && id / divided == id % divided
  }

  private fun fullCheck(id: Long): Boolean {
    val str = id.toString()
    return (1..5).any {
      str.length % it == 0 && str.length >= it * 2 && str.chunked(it).let { chunks ->
        chunks.all { chunk -> chunk == chunks[0] }
      }
    }
  }

}

fun main() {
  ProductIds().run()
}
