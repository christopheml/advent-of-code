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

package fr.christopheml.aoc2015.day02

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.MultiLineSolution
import fr.christopheml.aoc2015.day02.Present.Companion.toPresent

class Presents : MultiLineSolution<Int>(day = 2) {

  override fun partOne(input: Input.MultiLine) = input
    .map { it.toPresent() }
    .sumOf { it.paperSize }

  override fun partTwo(input: Input.MultiLine) = input
    .map { it.toPresent() }
    .sumOf { it.ribbonLength }

}

class Present(length: Int, width: Int, height: Int) {

  val paperSize: Int = listOf(length * width, length * height, width * height)
    .sorted()
    .let { 2 * it.sum() + it.first() }

  val ribbonLength = length * width * height + listOf(length, width, height).sorted().let { 2 * (it[0] + it[1]) }

  companion object {

    fun String.toPresent() = this.split("x")
      .map { it.toInt() }
      .let {
        Present(it[0], it[1], it[2])
      }

  }

}

fun main() {
  Presents().run()
}
