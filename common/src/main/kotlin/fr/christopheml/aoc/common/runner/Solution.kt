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

package fr.christopheml.aoc.common.runner

import arrow.core.Either
import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.InputFailure
import fr.christopheml.aoc.common.humanReadable
import fr.christopheml.aoc.common.multiLine
import fr.christopheml.aoc.common.singleLine
import kotlin.time.measureTimedValue

abstract class Solution<In : Input, Out>(
  val day: Int,
  private val inputReader: (Solution<In, Out>) -> Either<InputFailure, In>
) {

  abstract fun partOne(input: In): Out
  abstract fun partTwo(input: In): Out

  fun run() {
    println("Solution for day %02d".format(day))
    inputReader(this).map {
      runPart(1, it, this::partOne)
      runPart(2, it, this::partTwo)
    }.onLeft { println(it) }
  }

  private fun runPart(number: Int, input: In, runnable: (In) -> Out) {
    try {
      val (result, duration) = measureTimedValue { runnable(input) }

      val representation = when (result) {
        null -> "<no result>"
        is String -> result
        else -> result.toString()
      }
      println("\tPart $number (time: ${duration.humanReadable()}): $representation")
    } catch (_: NotImplementedError) {
      println("\tPart $number: <not implemented>")
    }
  }

}

abstract class SingleLineSolution<Out>(
  day: Int,
) : Solution<Input.SingleLine, Out>(day, ::singleLine)

abstract class MultiLineSolution<Out>(
  day: Int,
) : Solution<Input.MultiLine, Out>(day, ::multiLine)
