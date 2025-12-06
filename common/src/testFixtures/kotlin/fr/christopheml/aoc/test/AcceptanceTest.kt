/*
 * MIT License
 *
 * Copyright (c) 2015-2025 Christophe MICHEL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

  open val forPartOne: Collection<TestData<Out>> = emptyList()
  open val forPartTwo: Collection<TestData<Out>> = emptyList()

  abstract fun toInput(text: String): In

  @TestFactory
  fun partOne(): Stream<DynamicTest> {
    val solutionInstance = solution()

    return forPartOne.stream().map {
      dynamicTest(it.renderName()) {
        solutionInstance.partOne(toInput(it.input)) shouldBe it.expected
      }
    }
  }

  @TestFactory
  fun partTwo(): Stream<DynamicTest> {
    val solutionInstance = solution()

    return forPartTwo.stream().map {
      dynamicTest(it.renderName()) {
        solutionInstance.partTwo(toInput(it.input)) shouldBe it.expected
      }
    }
  }

  private fun TestData<Out>.renderName() = when (this) {
    is NamedTestExample<Out> -> name
    else -> toName().let { name -> """"$name" = $expected""" }
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
