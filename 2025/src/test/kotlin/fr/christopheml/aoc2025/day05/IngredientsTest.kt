package fr.christopheml.aoc2025.day05

import fr.christopheml.aoc.test.MultiLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class IngredientsTest : MultiLineAcceptanceTest<Long>(::Ingredients) {

  override val forPartOne = listOf(
    """
      3-5
      10-14
      16-20
      12-18

      1
      5
      8
      11
      17
      32
    """.trimIndent() shouldBecome 3L
  )

  override val forPartTwo = listOf(
    """
      3-5
      10-14
      16-20
      12-18
    """.trimIndent() shouldBecome 14L
  )

}
