package fr.christopheml.aoc2015.day03

import fr.christopheml.aoc.test.SingleLineAcceptanceTest
import fr.christopheml.aoc.test.tests

class DeliveriesTest : SingleLineAcceptanceTest<Int>(::Deliveries) {

  override val forPartOne = listOf(
    "One move" tests (">" to 2),
    "Square move" tests ("^>v<" to 4),
    "Up and down" tests ("^v^v^v^v^v" to 2)
  )

  override val forPartTwo = listOf(
    "One move" tests ("^v" to 3),
    "Square move" tests ("^>v<" to 3),
    "Up and down" tests ("^v^v^v^v^v" to 11)
  )

}
