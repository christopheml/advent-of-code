package fr.christopheml.aoc2015.day21

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.extractors.mapExtractor
import fr.christopheml.aoc.common.multiLine
import fr.christopheml.aoc.common.runner.Solution
import kotlin.math.max

class RpgSimulator : Solution<Input.MultiLine, Int>(
  day = 21,
  inputReader = ::multiLine,
){

  override fun partOne(input: Input.MultiLine): Int {
    val boss = parse(input)

    return combinations
      .filter { (_, hero) -> hero.beats(boss) }
      .map { it.first }.min()
  }

  override fun partTwo(input: Input.MultiLine): Int {
    val boss = parse(input)
    return combinations
      .filter { (_, hero) -> !hero.beats(boss) }
      .map { it.first }.max()
  }

  private val combinations = weapons.asSequence().flatMap { weapon ->
    armor.asSequence().flatMap { armor ->
      rings.asSequence().flatMap { ring1 ->
        rings.minus(ring1).asSequence().map { ring2 ->
          val equipment = listOf(weapon, armor, ring1, ring2)
          val price = equipment.sumOf { it.cost }
          val hero = Character.hero(equipment.sumOf { it.damage }, equipment.sumOf { it.armor })
          price to hero
        }
      }
    }
  }

  private fun parse(input: Input.MultiLine): Character =
    input.extract(mapExtractor(separator = ": ") { it.toInt() }).let {
      Character(it["Hit Points"]!!, it["Damage"]!!, it["Armor"]!!)
    }

}

data class Character(val hitPoints: Int, val damage: Int, val armor: Int) {
  fun beats(other: Character) = hitPoints.toFloat() / max(other.damage - armor, 1) >=
    other.hitPoints.toFloat() / max(damage - other.armor, 1)

  companion object {
    fun hero(damage: Int, armor: Int) = Character(100, damage, armor)
  }
}

data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

private val weapons = setOf(
  Item("Dagger", 8, 4, 0),
  Item("Shortsword", 10, 5, 0),
  Item("Warhammer", 25, 6, 0),
  Item("Longsword", 40, 7, 0),
  Item("Greataxe", 74, 8, 0)
)

private val armor = setOf(
  Item("No armor", 0, 0, 0),
  Item("Leather", 13, 0, 1),
  Item("Chainmail", 31, 0, 2),
  Item("Splintmail", 53, 0, 3),
  Item("Bandedmail", 75, 0, 4),
  Item("Platemail", 102, 0, 5)
)

private val rings = setOf(
  Item("No ring 1", 0, 0, 0),
  Item("No ring 2", 0, 0, 0),
  Item("Damage +1", 25, 1, 0),
  Item("Damage +2", 50, 2, 0),
  Item("Damage +3", 100, 3, 0),
  Item("Defense +1", 20, 0, 1),
  Item("Defense +2", 40, 0, 2),
  Item("Defense +3", 80, 0, 3)
)

fun main() {
  RpgSimulator().run()
}
