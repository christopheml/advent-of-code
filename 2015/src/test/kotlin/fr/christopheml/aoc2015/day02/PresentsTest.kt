package fr.christopheml.aoc2015.day02

import fr.christopheml.aoc.test.MultiLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class PresentsTest : MultiLineAcceptanceTest<Int>(::Presents) {

  override val forPartOne = listOf(
    "2x3x4" shouldBecome 58,
    "1x1x10" shouldBecome 43,
    "4x3x2" shouldBecome 58,
    "1x10x1" shouldBecome 43,
  )

  override val forPartTwo = listOf(
    "2x3x4" shouldBecome 34,
    "1x1x10" shouldBecome 14,
    "4x3x2" shouldBecome 34,
    "1x10x1" shouldBecome 14,
  )

}
