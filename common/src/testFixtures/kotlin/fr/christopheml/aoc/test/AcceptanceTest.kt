package fr.christopheml.aoc.test

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.Solution
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

abstract class AcceptanceTest<In : Input, Out>(
  val solution: () -> Solution<In, Out>,
) {

  open val forPartOne: Collection<TestExample<Out>> = emptyList()
  open val forPartTwo: Collection<TestExample<Out>> = emptyList()
  abstract fun toInput(text: String): In

  @TestFactory
  fun partOne(): Stream<DynamicTest> {
    val solutionInstance = solution()

    return forPartOne.stream().map {
      dynamicTest(it.name) {
        solutionInstance.partOne(toInput(it.testData.first)) shouldBe it.testData.second
      }
    }
  }

  @TestFactory
  fun partTwo(): Stream<DynamicTest> {
    val solutionInstance = solution()

    return forPartTwo.stream().map {
      dynamicTest(it.name) {
        solutionInstance.partTwo(toInput(it.testData.first)) shouldBe it.testData.second
      }
    }
  }

}

abstract class SingleLineAcceptanceTest<Out>(
  solution: () -> Solution<Input.SingleLine, Out>,
) : AcceptanceTest<Input.SingleLine, Out>(solution) {
  override fun toInput(text: String): Input.SingleLine = Input.SingleLine(text)
}

abstract class MultiLineAcceptanceTest<Out>(
  solution: () -> Solution<Input.MultiLine, Out>,
) : AcceptanceTest<Input.MultiLine, Out>(solution) {
  override fun toInput(text: String): Input.MultiLine = Input.MultiLine(text.lines())
}
