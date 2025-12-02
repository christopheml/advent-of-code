package fr.christopheml.aoc2025.day01

import fr.christopheml.aoc.test.MultiLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class PasswordTest : MultiLineAcceptanceTest<Int>(::Password) {

  override val forPartOne = listOf(
    """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
    """.trimIndent() shouldBecome 3
  )

  override val forPartTwo = listOf(
    """
      R1000
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82
    """.trimIndent() shouldBecome 16
  )

}
