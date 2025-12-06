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
