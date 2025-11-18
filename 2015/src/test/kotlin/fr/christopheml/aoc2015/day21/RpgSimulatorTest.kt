package fr.christopheml.aoc2015.day21

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class RpgSimulatorTest {


  @Test
  fun `Sample hero should beat sample boss`() {
    val sampleHero = Character(8, 5, 5)
    val sampleBoss = Character(12, 7, 2)
    sampleHero.beats(sampleBoss) shouldBe true
  }

  @Test
  fun `Sample hero should not beat better boss`() {
    val sampleHero = Character(8, 5, 5)
    val betterBoss = Character(20, 7, 2)
    sampleHero.beats(betterBoss) shouldBe false
  }

}
