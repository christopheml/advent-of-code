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

package fr.christopheml.aoc2025.day04

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.grid.IntGrid
import fr.christopheml.aoc.common.grid.toIntGrid
import fr.christopheml.aoc.common.runner.MultiLineSolution

class Forklifts : MultiLineSolution<Int>(day = 4) {

  override fun partOne(input: Input.MultiLine) = input.toGrid().countDeletable()

  override fun partTwo(input: Input.MultiLine) = input.toGrid().let { grid ->
    val original = grid.count { it > 0 }

    while(grid.countDeletable() > 0) {
      grid.forEach { x, y ->
        if (grid.isDeletable(x, y)) grid[x, y] = 0
      }
    }

    original - grid.count { it > 0 }
  }

  private fun IntGrid.countDeletable() = countIndexed { x, y, _ -> this.isDeletable(x, y) }

  private fun IntGrid.isDeletable(x: Int, y: Int) = this[x, y] > 0 && allNeighbors(x, y).count { it > 0 } < 4

  private fun Input.MultiLine.toGrid() = toIntGrid  { if (it == '@') 1 else 0 }

}

fun main() {
  Forklifts().run()
}
