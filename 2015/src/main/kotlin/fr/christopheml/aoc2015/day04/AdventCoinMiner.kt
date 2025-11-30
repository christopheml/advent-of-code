package fr.christopheml.aoc2015.day04

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.runner.Solution
import fr.christopheml.aoc.common.singleLine
import java.security.MessageDigest
import kotlin.experimental.and

class AdventCoinMiner : Solution<Input.SingleLine, Int>(
  day = 4,
  inputReader = ::singleLine,
) {

  private val md5 = MessageDigest.getInstance("MD5")
  private val zero = 0.toByte()
  private val halfZero = 0xF0.toByte()

  override fun partOne(input: Input.SingleLine) = findMatchingMd5(input.value, ::hasFiveLeadingZeroes)

  override fun partTwo(input: Input.SingleLine) = findMatchingMd5(input.value, ::hasSixLeadingZeroes)

  private fun findMatchingMd5(root: String, predicate: (ByteArray) -> Boolean): Int =
    generateSequence(1) { it + 1 }
      .map { root + it }
      .map { md5.digest(it.encodeToByteArray()) }
      .indexOfFirst(predicate) + 1


  private fun hasFiveLeadingZeroes(bytes: ByteArray) =
    bytes[0] == zero && bytes[1] == zero && (bytes[2] and halfZero) == zero

  private fun hasSixLeadingZeroes(bytes: ByteArray) =
    bytes[0] == zero && bytes[1] == zero && bytes[2] == zero

}

fun main() {
  AdventCoinMiner().run()
}
