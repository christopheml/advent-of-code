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

sealed interface Point<T> {

  val x: T

  val y: T

  fun top(): Point<T>

  fun bottom(): Point<T>

  fun left(): Point<T>

  fun right(): Point<T>

  fun topLeft(): Point<T>

  fun topRight(): Point<T>

  fun bottomLeft(): Point<T>

  fun bottomRight(): Point<T>

  fun directNeighbors() = sequenceOf(top(), right(), bottom(), left())

  fun diagonalNeighbors() = sequenceOf(topLeft(), topRight(), bottomRight(), bottomLeft())

  fun allNeighbors() = directNeighbors() + diagonalNeighbors()

  companion object {

    fun point(x: Int, y: Int): Point<Int> = IntPoint(x, y)

    fun point(x: Long, y: Long): Point<Long> = LongPoint(x, y)

  }

}

data class IntPoint(override val x: Int, override val y: Int) : Point<Int> {

  override fun top() = IntPoint(x - 1, y)

  override fun bottom() = IntPoint(x + 1, y)

  override fun left() = IntPoint(x, y - 1)

  override fun right() = IntPoint(x, y + 1)

  override fun topLeft() = IntPoint(x - 1, y - 1)

  override fun topRight() = IntPoint(x - 1, y + 1)

  override fun bottomLeft() = IntPoint(x + 1, y - 1)

  override fun bottomRight() = IntPoint(x + 1, y + 1)

}

data class LongPoint(override val x: Long, override val y: Long) : Point<Long> {

  override fun top() = LongPoint(x - 1, y)

  override fun bottom() = LongPoint(x + 1, y)

  override fun left() = LongPoint(x, y - 1)

  override fun right() = LongPoint(x, y + 1)

  override fun topLeft() = LongPoint(x - 1, y - 1)

  override fun topRight() = LongPoint(x - 1, y + 1)

  override fun bottomLeft() = LongPoint(x + 1, y - 1)

  override fun bottomRight() = LongPoint(x + 1, y + 1)

}
