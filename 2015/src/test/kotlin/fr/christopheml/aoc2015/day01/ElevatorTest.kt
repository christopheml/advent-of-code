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
