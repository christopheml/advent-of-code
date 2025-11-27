package fr.christopheml.aoc2015.day01

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.Solution
import fr.christopheml.aoc.common.singleLine

class Elevator : Solution<Input.SingleLine, Int>(
  day = 1,
  inputReader = ::singleLine
) {

  override fun partOne(input: Input.SingleLine): Int = endFloor(input.value)

  override fun partTwo(input: Input.SingleLine): Int = firstBasementEntry(input.value)

  fun endFloor(commands: String): Int {
    var floor = 0
    commands.rideElevator(
      onUp = { floor++ },
      onDown = { floor-- },
    )
    return floor
  }

  fun firstBasementEntry(commands: String): Int {
    var floor = 0
    var firstBasementEntry = 0
    commands.rideElevator(
      onUp = { floor++ },
      onDown = {
        floor--
        if (floor < 0 && firstBasementEntry == 0) {
          firstBasementEntry = it + 1
        }
      },
    )
    return firstBasementEntry
  }

  private fun String.rideElevator(onUp: (Int) -> Unit, onDown: (Int) -> Unit): Unit {
    for (i in this.indices) {
      if (this[i] == '(') {
        onUp(i)
      } else if (this[i] == ')') {
        onDown(i)
      }
    }
  }

}

fun main() {
  Elevator().run()
}
