package fr.christopheml.aoc.common

fun String.toIntRange() = split("-").map { it.toInt() }.let { it[0]..it[1] }

fun String.toLongRange() = split("-").map { it.toLong() }.let { it[0]..it[1] }

fun String.toDigits() = map { it.digitToInt() }
