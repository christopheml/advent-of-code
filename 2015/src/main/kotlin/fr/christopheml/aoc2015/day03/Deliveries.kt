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

package fr.christopheml.aoc2015.day03

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.collections.asSequence
import fr.christopheml.aoc.common.collections.map
import fr.christopheml.aoc.common.grid.Point
import fr.christopheml.aoc.common.grid.Point.Companion.point
import fr.christopheml.aoc.common.runner.SingleLineSolution

class Deliveries : SingleLineSolution<Int>(day = 3) {

  override fun partOne(input: Input.SingleLine) = input.value.distributePresents()

  override fun partTwo(input: Input.SingleLine) = input.value.distributePresentsParallel()

  private fun String.distributePresents(): Int = asSequence()
    .scan(point(0, 0)) { current, instruction -> current.move(instruction) }
    .distinct()
    .count()

  private fun String.distributePresentsParallel(): Int = asSequence()
    .chunked(2)
    .scan(Pair(point(0, 0), point(0, 0))) { current, instruction ->
      current.map({ it.move(instruction[0]) }, { it.move(instruction[1]) })
    }
    .flatMap { it.asSequence() }
    .distinct()
    .count()

  private fun Point<Int>.move(instruction: Char) = when (instruction) {
    '^' -> this.top()
    '<' -> this.left()
    '>' -> this.right()
    else -> this.bottom()
  }

}

fun main() {
  Deliveries().run()
}
