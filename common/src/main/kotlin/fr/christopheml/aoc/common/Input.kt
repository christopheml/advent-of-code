@file:Suppress("JavaDefaultMethodsNotOverriddenByDelegation")

package fr.christopheml.aoc.common

import arrow.core.Either
import arrow.core.Option
import fr.christopheml.aoc.common.Input.MultiLine
import fr.christopheml.aoc.common.extractors.Extractor
import fr.christopheml.aoc.common.runner.Solution

sealed interface Input {

  class MultiLine(private val lines: List<String>) : Input, List<String> by lines {

    fun <E> extract(extractor: Extractor<E>) = extractor.extract(lines)

  }
  class SingleLine : Input

}

fun <Out> multiline(solution: Solution<MultiLine, Out>): Either<InputFailure, MultiLine> {
  val resource = "/inputs/day%02d.txt".format(solution.day)
  return Option.fromNullable(solution::class.java.getResource(resource))
    .toEither { InputFailure("File $resource could not be read") }
    .map { it.readText(Charsets.UTF_8).lines() }
    .map { if (it.last().isEmpty()) it.dropLast(1) else it }
    .map { MultiLine(it) }
}

data class InputFailure(val message: String) {
  fun Throwable.toFailure(): InputFailure = InputFailure(this.message ?: "Unknown error")
}
