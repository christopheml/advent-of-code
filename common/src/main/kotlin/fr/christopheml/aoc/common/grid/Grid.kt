/*
 * MIT License
 *
 * Copyright (c) 2015-2025 Christophe MICHEL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.christopheml.aoc.common.grid

import fr.christopheml.aoc.common.grid.Point.Companion.point

class Grid<T>(val lines: List<List<T>>) {

  val width = lines[0].size
  val height = lines.size
  val horizontalIndices = 0..<width
  val verticalIndices = 0..<height

  val size = width * height

  private fun inRange(line: Int, column: Int) = line in verticalIndices && column in horizontalIndices

  fun inRange(point: Point<Int>) = inRange(point.x, point.y)

  fun points() = sequence {
    for (x in verticalIndices) {
      for (y in horizontalIndices) {
        yield(point(x, y))
      }
    }
  }

  fun <R> map(transform: (element: T) -> R): Grid<R> {
    return Grid(
      lines.map { line ->
        line.map { transform(it) }
      }.toList()
    )
  }

  fun <R> mapIndexed(transform: (point: Point<Int>, element: T) -> R): Grid<R> {
    return Grid(
      lines.mapIndexed { x, line ->
        line.mapIndexed { y, element -> transform(point(x, y), element) }
      }.toList()
    )
  }

  /**
   * Returns a [Sequence] of [points][Point] matching the given predicate.
   *
   * This is more efficient than calling [points] then [Sequence.filter].
   */
  fun filter(predicate: (T) -> Boolean) = sequence {
    for (x in verticalIndices) {
      for (y in horizontalIndices) {
        if (predicate(get(x, y))) yield(point(x, y))
      }
    }
  }

  fun toList() = lines

  fun line(index: Int) = lines[index].asSequence()

  fun column(index: Int) = sequence {
    for (x in verticalIndices) {
      yield(lines[x][index])
    }
  }

  fun transpose() = Grid(
    horizontalIndices.map { column(it).toList() }.toList()
  )

  fun directNeighbors(point: Point<Int>) = point.directNeighbors().filter { inRange(it) }

  fun diagonalNeighbors(point: Point<Int>) = point.diagonalNeighbors().filter { inRange(it) }

  fun allNeighbors(point: Point<Int>) = point.allNeighbors().filter { inRange(it) }

  operator fun get(line: Int, column: Int): T = lines[line][column]

  operator fun get(point: Point<Int>): T = lines[point.x][point.y]

  /**
   * Returns the coordinates of the first occurrence of the given element
   */
  fun first(element: T): Point<Int> {
    for (line in verticalIndices) {
      for (column in horizontalIndices) {
        if (lines[line][column] == element) return point(line, column)
      }
    }
    throw NoSuchElementException("Element '$element' not found in the grid")
  }

  fun next(point: Point<Int>, direction: Direction): Point<Int>? {
    val next = direction.next(point)
    return if (inRange(next)) next else null
  }

  fun test(point: Point<Int>, predicate: (T) -> Boolean): Boolean = inRange(point) && predicate.invoke(this[point])

}
