package fr.christopheml.aoc2015.day04

import fr.christopheml.aoc.test.SingleLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class AdventCoinMinerTest : SingleLineAcceptanceTest<Int>(::AdventCoinMiner) {

  override val forPartOne = listOf(
    "abcdef" shouldBecome 609043,
    "pqrstuv" shouldBecome 1048970,
  )

}
