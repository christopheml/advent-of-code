package fr.christopheml.aoc.common.grid

sealed interface Point {

  fun top(): Point

  fun bottom(): Point

  fun left(): Point

  fun right(): Point

  fun topLeft(): Point

  fun topRight(): Point

  fun bottomLeft(): Point

  fun bottomRight(): Point

  fun directNeighbors() = listOf(top(), right(), bottom(), left())

  fun diagonalNeighbors() = listOf(topLeft(), topRight(), bottomRight(), bottomLeft())

  fun allNeighbors() = listOf(top(), topRight(), right(), bottomRight(), bottom(), bottomLeft(), left(), topLeft())

  companion object {

    fun point(x: Int, y: Int): Point = IntPoint(x, y)

    fun point(x: Long, y: Long): Point = LongPoint(x, y)

  }

}

data class IntPoint(val x: Int, val y: Int) : Point {

  override fun top() = IntPoint(x - 1, y)

  override fun bottom() = IntPoint(x + 1, y)

  override fun left() = IntPoint(x, y - 1)

  override fun right() = IntPoint(x, y + 1)

  override fun topLeft() = IntPoint(x - 1, y - 1)

  override fun topRight() = IntPoint(x - 1, y + 1)

  override fun bottomLeft() = IntPoint(x + 1, y - 1)

  override fun bottomRight() = IntPoint(x + 1, y + 1)

}

data class LongPoint(val x: Long, val y: Long) : Point {

  override fun top() = LongPoint(x - 1, y)

  override fun bottom() = LongPoint(x + 1, y)

  override fun left() = LongPoint(x, y - 1)

  override fun right() = LongPoint(x, y + 1)

  override fun topLeft() = LongPoint(x - 1, y - 1)

  override fun topRight() = LongPoint(x - 1, y + 1)

  override fun bottomLeft() = LongPoint(x + 1, y - 1)

  override fun bottomRight() = LongPoint(x + 1, y + 1)

}
