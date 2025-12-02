package fr.christopheml.aoc2025.day02

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.Solution
import fr.christopheml.aoc.common.singleLine
import fr.christopheml.aoc.common.toLongRange
import kotlin.math.log10
import kotlin.math.pow

class ProductIds : Solution<Input.SingleLine, Long>(
  day = 2,
  inputReader = ::singleLine
) {

  override fun partOne(input: Input.SingleLine): Long = input.checkIds(::partialCheck)

  override fun partTwo(input: Input.SingleLine): Long = input.checkIds(::fullCheck)

  private fun Input.SingleLine.checkIds(predicate: (Long) -> Boolean): Long =
    value.split(",")
      .map { it.toLongRange() }
      .flatMap { it.filter(predicate) }
      .sum()

  private fun partialCheck(id: Long): Boolean {
    val digits = log10(id.toDouble()).toInt() + 1
    val divided = 10.0.pow(digits / 2).toInt()
    return id > 10 && id / divided == id % divided
  }

  private fun fullCheck(id: Long): Boolean {
    val str = id.toString()
    return (1..5).any {
      str.length % it == 0 && str.length >= it * 2 && str.chunked(it).let { chunks ->
        chunks.all { chunk -> chunk == chunks[0] }
      }
    }
  }

}

fun main() {
  ProductIds().run()
}
