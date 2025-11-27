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

  @JvmInline
  value class SingleLine(val value: String) : Input

}

fun <Out> multiLine(solution: Solution<MultiLine, Out>): Either<InputFailure, MultiLine> =
    readInputFile(solution)
    .map { MultiLine(it) }

fun <Out> singleLine(solution: Solution<Input.SingleLine, Out>): Either<InputFailure, Input.SingleLine> =
    readInputFile(solution)
    .map { Input.SingleLine(it.first()) }

fun <Out> readInputFile(solution: Solution<out Input, Out>): Either<InputFailure, List<String>> =
  "/inputs/day%02d.txt".format(solution.day).let { resource ->
    Option.fromNullable(solution::class.java.getResource(resource))
      .toEither { InputFailure("File $resource could not be read") }
      .map { it.readText(Charsets.UTF_8).lines() }
  }

data class InputFailure(val message: String) {
  fun Throwable.toFailure(): InputFailure = InputFailure(this.message ?: "Unknown error")
}
