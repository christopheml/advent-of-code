package fr.christopheml.aoc.common.collections

fun <T, R> Pair<T, T>.map(transform: (T) -> R): Pair<R, R> = Pair(transform(first), transform(second))

fun <T, R> Pair<T, T>.map(transformFirst: (T) -> R, transformSecond: (T) -> R): Pair<R, R> =
  Pair(transformFirst(first), transformSecond(second))

fun <T> Pair<T, T>.asSequence(): Sequence<T> = sequenceOf(first, second)
