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
    .map { it.filter(String::isNotEmpty) }
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
