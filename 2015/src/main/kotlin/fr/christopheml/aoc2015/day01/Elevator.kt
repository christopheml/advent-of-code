package fr.christopheml.aoc2015.day01

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.Solution
import fr.christopheml.aoc.common.singleLine

class Elevator : Solution<Input.SingleLine, Int>(
  day = 1,
  inputReader = ::singleLine
) {

  override fun partOne(input: Input.SingleLine): Int = input.value
    .asSequence()
    .fold(0, ::move)

  override fun partTwo(input: Input.SingleLine): Int = input.value
    .asSequence()
    .scan(0, ::move)
    .indexOfFirst { it < 0 }

  private fun move(floor: Int, operation: Char) = when (operation) {
    '(' -> floor + 1
    else -> floor - 1
  }

}

fun main() {
  Elevator().run()
}
