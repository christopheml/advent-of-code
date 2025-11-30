package fr.christopheml.aoc2015.day03

import fr.christopheml.aoc.test.SingleLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class DeliveriesTest : SingleLineAcceptanceTest<Int>(::Deliveries) {

  override val forPartOne = listOf(
    ">" shouldBecome 2,
    "^>v<" shouldBecome 4,
    "^v^v^v^v^v" shouldBecome 2,
  )

  override val forPartTwo = listOf(
    "^v" shouldBecome 3,
    "^>v<" shouldBecome 3,
    "^v^v^v^v^v" shouldBecome 11,
  )

}
