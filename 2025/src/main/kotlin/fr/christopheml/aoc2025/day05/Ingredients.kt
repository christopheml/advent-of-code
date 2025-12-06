package fr.christopheml.aoc2025.day05

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.ranges.merge
import fr.christopheml.aoc.common.ranges.overlaps
import fr.christopheml.aoc.common.runner.MultiLineSolution
import fr.christopheml.aoc2025.day05.Ingredients.Database.Companion.toDatabase
import fr.christopheml.aoc2025.day05.Ingredients.Database.Companion.toFreshness

class Ingredients : MultiLineSolution<Long>(day = 5) {

  override fun partOne(input: Input.MultiLine) = input.toDatabase().countFresh()

  override fun partTwo(input: Input.MultiLine) = input.toFreshness()
    .sortedBy { it.first }
    .fold(RangeFolder.initial(), RangeFolder::accumulate)
    .resultingRanges()
    .sumOf { it.last - it.first + 1 }

  private data class Database(val freshness: List<LongRange>, val ingredientIds: List<Long>) {

    fun countFresh() = ingredientIds.count { freshness.any { range -> range.contains(it) } }.toLong()

    companion object {

      fun Input.MultiLine.toDatabase() = Database(
        freshness = this@toDatabase.toFreshness(),
        ingredientIds = this@toDatabase.toIngredientIds()
      )

      fun Input.MultiLine.toFreshness() = this.filter { it.contains("-") }
        .map { it.split("-") }
        .map { LongRange(it[0].toLong(), it[1].toLong()) }

      fun Input.MultiLine.toIngredientIds() = this.filter { !it.contains("-") && it.isNotEmpty() }
        .map { it.toLong() }

    }

  }

  data class RangeFolder(val ranges: List<LongRange>, val last: LongRange? = null) {

    fun resultingRanges(): List<LongRange> = buildList {
      addAll(ranges)
      if (last != null) add(last)
    }

    companion object {

      fun initial() = RangeFolder(ranges = listOf(), last = null)

      fun accumulate(folder: RangeFolder, next: LongRange): RangeFolder {
        if (folder.last == null) return folder.copy(last = next)
        return if (folder.last.overlaps(next)) {
          folder.copy(last = folder.last.merge(next))
        } else {
          folder.copy(ranges = folder.resultingRanges(), last = next)
        }
      }

    }

  }

}

fun main() {
  Ingredients().run()
}
