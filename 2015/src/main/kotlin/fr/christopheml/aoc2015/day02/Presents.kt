package fr.christopheml.aoc2015.day02

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.multiLine
import fr.christopheml.aoc.common.runner.Solution
import fr.christopheml.aoc2015.day02.Present.Companion.toPresent

class Presents : Solution<Input.MultiLine, Int>(
  day = 2,
  inputReader = ::multiLine
) {

  override fun partOne(input: Input.MultiLine) = input
    .map { it.toPresent() }
    .sumOf { it.paperSize }

  override fun partTwo(input: Input.MultiLine) = input
    .map { it.toPresent() }
    .sumOf { it.ribbonLength }

}

class Present(length: Int, width: Int, height: Int) {

  val paperSize: Int = listOf(length * width, length * height, width * height)
    .sorted()
    .let { 2 * it.sum() + it.first() }

  val ribbonLength = length * width * height + listOf(length, width, height).sorted().let { 2 * (it[0] + it[1]) }

  companion object {

    fun String.toPresent() = this.split("x")
      .map { it.toInt() }
      .let {
        Present(it[0], it[1], it[2])
      }

  }

}

fun main() {
  Presents().run()
}
