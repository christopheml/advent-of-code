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

