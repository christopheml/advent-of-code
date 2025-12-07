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

package fr.christopheml.aoc2025.day07

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.MultiLineSolution

class TachyonManifold : MultiLineSolution<Long>(day = 7) {

  override fun partOne(input: Input.MultiLine): Long {
    var splits = 0L
    Manifold.fromInput(input).split { splits++ }
    return splits
  }

  override fun partTwo(input: Input.MultiLine) = Manifold.fromInput(input).splitTimelinesRecursive()

  data class Manifold(val start: Int, val splitters: List<Set<Int>>) {

    fun split(onSplitAction: () -> Unit = {}) = splitters.fold(setOf(start)) { beams, splitter ->
      beams.flatMap {
        if (splitter.contains(it)) {
          onSplitAction()
          setOf(it - 1, it + 1)
        } else setOf(it)
      }.toSet()
    }

    fun splitTimelinesRecursive(): Long {
      val memory = mutableMapOf<Int, Long>()

      fun index(depth: Int, position: Int) = depth * 10000 + position

      fun next(depth: Int, position: Int): Long = memory.getOrPut(index(depth, position)) {
        if (depth > splitters.size - 1) 1L
        else if (splitters[depth].contains(position)) next(depth + 1, position - 1) + next(depth + 1, position + 1)
        else next(depth + 1, position)
      }

      return next(0, start)
    }

    companion object {
      fun fromInput(input: Input.MultiLine) = Manifold(
        start = input.first().indexOfFirst { it == 'S' },
        splitters = input.drop(1).map { line ->
          line.mapIndexed { i, c -> if (c == '^') i else - 1 }.filter { it != -1 }.toSet()
        }.filter { it.isNotEmpty() }
      )
    }
  }

}

fun main() {
  TachyonManifold().run()
}
