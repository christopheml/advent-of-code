package fr.christopheml.aoc2025.day02

import fr.christopheml.aoc.test.SingleLineAcceptanceTest
import fr.christopheml.aoc.test.shouldBecome

class ProductIdsTest : SingleLineAcceptanceTest<Long>(::ProductIds) {

  override val forPartOne = listOf(
    "5-15" shouldBecome 11L,
    "11-22" shouldBecome 33L,
    "95-115" shouldBecome 99L,
    "998-1012" shouldBecome 1010L,
    "1188511880-1188511890" shouldBecome 1188511885L,
    "222220-222224" shouldBecome 222222L,
    "1698522-1698528" shouldBecome 0L,
    "446443-446449" shouldBecome 446446L,
    "38593856-38593862" shouldBecome 38593859L,
    "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
      "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
      "824824821-824824827,2121212118-2121212124" shouldBecome 1227775554L,
  )

  override val forPartTwo = listOf(
    "5-15" shouldBecome 11L,
    "11-22" shouldBecome 33L,
    "95-115" shouldBecome 210L,
    "998-1012" shouldBecome 2009L,
    "1188511880-1188511890" shouldBecome 1188511885L,
    "222220-222224" shouldBecome 222222L,
    "1698522-1698528" shouldBecome 0L,
    "446443-446449" shouldBecome 446446L,
    "38593856-38593862" shouldBecome 38593859L,
    "565653-565659" shouldBecome 565656L,
    "824824821-824824827" shouldBecome 824824824L,
    "2121212118-2121212124" shouldBecome 2121212121L,
    "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
      "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
      "824824821-824824827,2121212118-2121212124" shouldBecome 4174379265L,
  )

}
