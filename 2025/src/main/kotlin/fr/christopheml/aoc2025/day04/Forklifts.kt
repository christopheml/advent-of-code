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
