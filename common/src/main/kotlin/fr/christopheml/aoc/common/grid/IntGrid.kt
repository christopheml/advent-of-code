package fr.christopheml.aoc.common.grid

import fr.christopheml.aoc.common.Input

class IntGrid(initialValues: List<List<Int>>) {

  private val grid: Array<IntArray> = initialValues.map { it.toIntArray() }.toTypedArray()

  private val xIndices = grid[0].indices
  private val yIndices = grid.indices

  operator fun get(x: Int, y: Int) = grid[y][x]
  operator fun set(x: Int, y: Int, value: Int) { grid[y][x] = value }

  fun forEach(action: (Int, Int) -> Unit) {
    for (y in yIndices) for (x in xIndices) action(x, y)
  }

  fun count(predicate: (Int) -> Boolean): Int =
    filterValues(predicate).count()

  fun countIndexed(predicate: (x: Int, y: Int, value: Int) -> Boolean) =
    filterValuesIndexed(predicate).count()

  fun mapValuesIndexed(transform: (tX: Int, tY: Int, value: Int) -> Int) = sequence {
    for (y in yIndices) for (x in xIndices) yield(transform(x, y, this@IntGrid[x, y]))
  }

  fun filterValues(predicate: (Int) -> Boolean) = sequence {
    for (y in yIndices) for (x in xIndices) if (predicate(this@IntGrid[x, y])) yield(this@IntGrid[x, y])
  }

  fun filterValuesIndexed(predicate: (x: Int, y: Int, value: Int) -> Boolean) = sequence {
    for (y in yIndices) for (x in xIndices) if (predicate(x, y, this@IntGrid[x, y])) yield(this@IntGrid[x, y])
  }

  fun transform(x: Int, y: Int, operation: (tX: Int, tY: Int, value: Int) -> Int) {
    this[x, y] = operation(x, y, this[x, y])
  }

  fun directNeighbors(x: Int, y: Int): Sequence<Int> = sequence {
    if (x + 1 in xIndices) yield(this@IntGrid[x + 1, y])
    if (x + -1 in xIndices) yield(this@IntGrid[x - 1, y])
    if (y + 1 in yIndices) yield(this@IntGrid[x, y + 1])
    if (y - 1 in yIndices) yield(this@IntGrid[x, y - 1])
  }

  fun diagonalNeighbors(x: Int, y: Int): Sequence<Int> = sequence {
    if (x + 1 in xIndices && y + 1 in yIndices) yield(this@IntGrid[x + 1, y + 1])
    if (x + 1 in xIndices && y - 1 in yIndices) yield(this@IntGrid[x + 1, y - 1])
    if (x - 1 in xIndices && y + 1 in yIndices) yield(this@IntGrid[x - 1, y + 1])
    if (x - 1 in xIndices && y - 1 in yIndices) yield(this@IntGrid[x - 1, y - 1])
  }

  fun allNeighbors(x: Int, y: Int): Sequence<Int> = directNeighbors(x, y) + diagonalNeighbors(x, y)

  override fun toString() = grid.joinToString("\n") { line -> line.joinToString(" ") }

}

fun Input.MultiLine.toIntGrid(decoder: (Char) -> Int) = IntGrid(this.map { line -> line.map(decoder) })
