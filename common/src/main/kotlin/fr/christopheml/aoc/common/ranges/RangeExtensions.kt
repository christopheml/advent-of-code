package fr.christopheml.aoc.common.ranges

fun <T : Comparable<T>> ClosedRange<T>.overlaps(other: ClosedRange<T>): Boolean =
  this.contains(other.start) || this.contains(other.endInclusive) || other.contains(start) || other.contains(endInclusive)

fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>): Boolean =
  this.contains(other.start) && this.contains(other.endInclusive)

fun <T : Comparable<T>> ClosedRange<T>.merge(other: ClosedRange<T>, instance: (start: T, endInclusive: T) -> ClosedRange<T>): ClosedRange<T> {
  require(this.overlaps(other)) { "$this and $other must overlap to be merged." }
  if (this.contains(other)) { return this }
  if (other.contains(this)) { return other }
  if (this.contains(other.start)) return instance(this.start, other.endInclusive)
  if (other.contains(start)) return instance(other.start, this.endInclusive)
  error("Cannot merge $this and $other")
}

fun IntRange.merge(other: IntRange) = merge(other, Int::rangeTo) as IntRange
fun LongRange.merge(other: LongRange) = merge(other, Long::rangeTo) as LongRange
fun CharRange.merge(other: CharRange) = merge(other, Char::rangeTo) as CharRange
@JvmName("mergeFloat")
fun ClosedFloatingPointRange<Float>.merge(other: ClosedFloatingPointRange<Float>) =
  merge(other, Float::rangeTo) as ClosedFloatingPointRange<Float>
@JvmName("mergeDouble")
fun ClosedFloatingPointRange<Double>.merge(other: ClosedFloatingPointRange<Double>) =
  merge(other, Double::rangeTo) as ClosedFloatingPointRange<Double>
