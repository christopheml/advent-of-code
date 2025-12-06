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

import kotlin.text.Typography.ellipsis

sealed interface TestData<T> {
  val input: String
  val expected: T
}

data class UnnamedTestExample<T>(override val input: String, override val expected: T) : TestData<T>

data class NamedTestExample<T>(val name: String, override val input: String, override val expected: T) : TestData<T>

fun TestData<*>.toName() = when (this) {
  is UnnamedTestExample<*> -> if (input.length <= 20) input else input.take(19) + ellipsis
  is NamedTestExample<*> -> name
}

infix fun <T> String.with(testData: () -> UnnamedTestExample<T>) = testData.invoke().let {
  NamedTestExample(this@with, it.input, it.expected)
}

infix fun <T> String.shouldBecome(expected: T) = UnnamedTestExample(this, expected)

