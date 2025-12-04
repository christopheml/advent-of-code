package fr.christopheml.aoc2025.day04

import fr.christopheml.aoc.test.MultiLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

private val sample =
  """
    ..@@.@@@@.
    @@@.@.@.@@
    @@@@@.@.@@
    @.@@@@..@.
    @@.@@@@.@@
    .@@@@@@@.@
    .@.@.@.@@@
    @.@@@.@@@@
    .@@@@@@@@.
    @.@.@@@.@.
  """.trimIndent()

class ForkliftsTest : MultiLineAcceptanceTest<Int>(::Forklifts) {

  override val forPartOne = listOf(
    sample shouldBecome 13
  )

  override val forPartTwo = listOf(
    sample shouldBecome 43
  )

}
