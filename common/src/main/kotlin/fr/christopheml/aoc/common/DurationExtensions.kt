package fr.christopheml.aoc.common

import kotlin.time.Duration

fun Duration.humanReadable(): String =
  toComponents { minutes, seconds, nanoseconds ->
    if (minutes > 0) {
      "${minutes}m${seconds}s"
    } else if (seconds > 0) {
      "${seconds}s${nanoseconds / 1000000}ms"
    } else {
      "${nanoseconds / 1000000}ms"
    }
  }

