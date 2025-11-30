package fr.christopheml.aoc.test

data class TestExample<T>(val name: String, val testData: Pair<String, T>)

infix fun <T> String.tests(testData: Pair<String, T>) = TestExample(this, testData)
