package fr.christopheml.aoc.common.extractors

fun interface Extractor<E> {
  fun extract(input: Collection<String>): E
}
