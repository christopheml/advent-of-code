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

package fr.christopheml.aoc2015.day01

import fr.christopheml.aoc.test.SingleLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class ElevatorTest : SingleLineAcceptanceTest<Int>(::Elevator) {

  override val forPartOne = listOf(
    "(())" shouldBecome 0,
    "()()" shouldBecome 0,
    "(((" shouldBecome 3,
    "(()(()(" shouldBecome 3,
    "))(((((" shouldBecome 3,
    "())" shouldBecome -1,
    "))(" shouldBecome -1,
    ")))" shouldBecome -3,
    ")())())" shouldBecome -3,
  )

  override val forPartTwo = listOf(
    ")" shouldBecome 1,
    "()())" shouldBecome 5,
  )

}
