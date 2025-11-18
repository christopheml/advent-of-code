package fr.christopheml.aoc.common.extractors

fun colonKeyValue(input: Collection<String>) {
  input.map {
    val (key, value) = it.split(": ")
  }

}
