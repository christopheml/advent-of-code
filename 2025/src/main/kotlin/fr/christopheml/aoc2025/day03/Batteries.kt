package fr.christopheml.aoc2025.day03

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.MultiLineSolution
import fr.christopheml.aoc.common.toDigits

class Batteries : MultiLineSolution<Long>(day = 3) {

  override fun partOne(input: Input.MultiLine) = input
    .map { it.toDigits() }
    .sumOf { it.toJoltage() }

  override fun partTwo(input: Input.MultiLine) = input
    .map { it.toDigits() }
    .sumOf { it.toMaxJoltage() }

  private fun List<Int>.toJoltage(): Long {
    val (firstDigit, others) = findDigit(this, 1)
    val secondDigit = others.max()
    return firstDigit * 10L + secondDigit
  }

  private fun List<Int>.toMaxJoltage(): Long = buildList {
    var current = this@toMaxJoltage
    for (remaining in 11 downTo 0) {
      val (digit, others) = findDigit(current, remaining)
      add(digit)
      current = others
    }
  }.fold(0L) { acc, digit -> acc * 10L + digit }

  private fun findDigit(digits: List<Int>, remaining: Int): Pair<Int, List<Int>> {
    val digit = digits.dropLast(remaining).max()
    return Pair(digit, digits.drop(digits.indexOf(digit) + 1))
  }

}

fun main() {
  Batteries().run()
}
