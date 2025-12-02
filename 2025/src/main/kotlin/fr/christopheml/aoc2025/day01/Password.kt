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
