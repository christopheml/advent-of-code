package fr.christopheml.aoc2025.day03

import fr.christopheml.aoc.test.MultiLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class BatteriesTest : MultiLineAcceptanceTest<Long>(::Batteries) {

  override val forPartOne = listOf(
    "987654321111111" shouldBecome 98L,
    "811111111111119" shouldBecome 89L,
    "234234234234278" shouldBecome 78L,
    "818181911112111" shouldBecome 92L,
  )

  override val forPartTwo = listOf(
    "987654321111111" shouldBecome 987654321111,
    "811111111111119" shouldBecome 811111111119,
    "234234234234278" shouldBecome 434234234278,
    "818181911112111" shouldBecome 888911112111,
  )

}
