package fr.christopheml.aoc2015.day02

import fr.christopheml.aoc.common.Input
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PresentsTest {

  @DisplayName("Day 2 part 1 acceptance test")
  @ParameterizedTest(name = "{0} needs {1} square feet of paper")
  @CsvSource(
    "2x3x4, 58", "1x1x10, 43", "4x3x2, 58", "1x10x1, 43"
  )
  fun `should work with part 1 examples`(present: String, expectedPaperSize: Int) {
    Presents().partOne(Input.MultiLine(listOf(present))) shouldBe expectedPaperSize
  }

  @DisplayName("Day 2 part 2 acceptance test")
  @ParameterizedTest(name = "{0} needs {1} feet of ribbon")
  @CsvSource(
    "2x3x4, 34", "1x1x10, 14", "4x3x2, 34", "1x10x1, 14"
  )
  fun `should work with part 2 examples`(present: String, expectedRibbonLength: Int) {
    Presents().partTwo(Input.MultiLine(listOf(present))) shouldBe expectedRibbonLength
  }

}
