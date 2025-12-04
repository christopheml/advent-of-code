package fr.christopheml.aoc.common.grid

enum class Direction {

  Top {
    override fun <T> next(point: Point<T>) = point.top()
  },
  Right {
    override fun <T> next(point: Point<T>) = point.right()
  },
  Bottom {
    override fun <T> next(point: Point<T>) = point.bottom()
  },
  Left {
    override fun <T> next(point: Point<T>) = point.left()
  };

  abstract fun <T> next(point: Point<T>): Point<T>

}
