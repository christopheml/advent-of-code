package fr.christopheml.aoc2015.day01

import fr.christopheml.aoc.common.Input
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ElevatorTest {

  @ParameterizedTest(name = "{0} is floor {1}")
  @CsvSource(
    "(()), 0", "()(), 0", "(((, 3", "(()(()(, 3", "))(((((, 3", "()), -1", "))(, -1", "))), -3", ")())()), -3"
  )
  fun `should work with part 1 examples`(input: String, expectedFloor: Int) {
    Elevator().partOne(Input.SingleLine(input)) shouldBe expectedFloor
  }

  @DisplayName("Day 1 part 2 acceptance test")
  @ParameterizedTest(name = "{0} basement entered on char {1}")
  @CsvSource(
    "), 1", "()()), 5"
  )
  fun `should work with part 2 examples`(input: String, expectedBasementEntry: Int) {
    Elevator().partTwo(Input.SingleLine(input)) shouldBe expectedBasementEntry
  }

}
