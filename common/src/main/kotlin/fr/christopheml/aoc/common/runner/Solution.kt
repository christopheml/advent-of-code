package fr.christopheml.aoc.common.runner

import arrow.core.Either
import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.InputFailure
import fr.christopheml.aoc.common.humanReadable
import kotlin.time.measureTimedValue

abstract class Solution<In : Input, Out>(
  val day: Int,
  private val inputReader: (Solution<In, Out>) -> Either<InputFailure, In>
) {

  abstract fun partOne(input: In): Out
  abstract fun partTwo(input: In): Out

  fun run() {
    println("Solution for day %02d".format(day))
    inputReader(this).map {
      runPart(1, it, this::partOne)
      runPart(2, it, this::partTwo)
    }.onLeft { println(it) }
  }

  private fun runPart(number: Int, input: In, runnable: (In) -> Out) {
    try {
      val (result, duration) = measureTimedValue { runnable(input) }

      val representation = when (result) {
        null -> "<no result>"
        is String -> result
        else -> result.toString()
      }
      println("\tPart $number (time: ${duration.humanReadable()}): $representation")
    } catch (_: NotImplementedError) {
      println("\tPart $number: <not implemented>")
    }
  }

}
