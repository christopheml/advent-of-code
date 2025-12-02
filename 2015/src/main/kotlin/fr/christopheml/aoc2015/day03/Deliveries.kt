package fr.christopheml.aoc2015.day03

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.collections.asSequence
import fr.christopheml.aoc.common.collections.map
import fr.christopheml.aoc.common.grid.Point
import fr.christopheml.aoc.common.runner.SingleLineSolution

class Deliveries : SingleLineSolution<Int>(day = 3) {

  override fun partOne(input: Input.SingleLine) = input.value.distributePresents()

  override fun partTwo(input: Input.SingleLine) = input.value.distributePresentsParallel()

  private fun String.distributePresents(): Int = asSequence()
    .scan(Point.of(0, 0)) { current, instruction -> current.move(instruction) }
    .distinct()
    .count()

  private fun String.distributePresentsParallel(): Int = asSequence()
    .chunked(2)
    .scan(Pair(Point.of(0, 0), Point.of(0, 0))) { current, instruction ->
      current.map({ it.move(instruction[0]) }, { it.move(instruction[1]) })
    }
    .flatMap { it.asSequence() }
    .distinct()
    .count()

  private fun Point.move(instruction: Char) = when (instruction) {
    '^' -> this.top()
    '<' -> this.left()
    '>' -> this.right()
    else -> this.bottom()
  }

}

fun main() {
  Deliveries().run()
}
