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

package fr.christopheml.aoc2025.day01

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.MultiLineSolution

class Password : MultiLineSolution<Int>(day = 1) {

  override fun partOne(input: Input.MultiLine): Int = rotate(input)
    .count { it % 100 == 0 }

  override fun partTwo(input: Input.MultiLine): Int = rotate(input)
    .windowed(2, 1)
    .sumOf { clicksOnZero(it[0], it[1]) }

  private fun rotate(input: Input.MultiLine): Sequence<Int> = input.asSequence()
    .map { it.toRotation() }
    .scan(50, Int::plus)

  private fun String.toRotation(): Int = this.drop(1).toInt() * if (this[0] == 'L') -1 else 1

  private fun clicksOnZero(origin: Int, destination: Int): Int =
    (if (origin < destination) origin..destination else origin downTo destination)
      .drop(1)
      .count { it % 100 == 0 }

}

fun main() {
  Password().run()
}
