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

package fr.christopheml.aoc2025.day09

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.asIntPoints
import fr.christopheml.aoc.common.grid.Point
import fr.christopheml.aoc.common.runner.MultiLineSolution
import kotlin.math.abs

class MovieTheater : MultiLineSolution<Long>(day = 9) {

  override fun partOne(input: Input.MultiLine) = input.asIntPoints().let { points ->
    points.asSequence().flatMapIndexed { i, point ->
      points.asSequence().drop(i + 1).map { rectangleArea(point, it) }
    }.max()
  }

  private fun rectangleArea(a: Point<Int>, b: Point<Int>): Long =
    (abs(a.x - b.x).toLong() + 1) * (abs(a.y - b.y) + 1)

  override fun partTwo(input: Input.MultiLine): Long {
    TODO("Not yet implemented")
  }

}

fun main() {
  MovieTheater().run()
}
