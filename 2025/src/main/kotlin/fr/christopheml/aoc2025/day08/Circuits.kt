/*
 * MIT License
 *
 * Copyright (c) 2025 Christophe MICHEL
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

package fr.christopheml.aoc2025.day08

import fr.christopheml.aoc.common.Input
import fr.christopheml.aoc.common.math.multiply
import fr.christopheml.aoc.common.runner.MultiLineSolution
import fr.christopheml.aoc.common.space.Point3D

class Circuits(private val connectionsLimit: Int = 1000) : MultiLineSolution<Long>(day = 8) {

  override fun partOne(input: Input.MultiLine) = input.makeConnections(applyLimit = true).circuitSizes()

  override fun partTwo(input: Input.MultiLine) = input.makeConnections(applyLimit = false).distanceFromWall()

  private fun Input.MultiLine.makeConnections(
    applyLimit: Boolean,
  ) = this.toJunctions()
    .let { junctions ->
      junctions.distances()
        .let { if (applyLimit) it.take(connectionsLimit) else it }
        .fold(CircuitFolder(junctions)) { cf, entry ->
          cf.fold(entry.first, entry.second)
        }
    }

  private fun List<Point3D>.distances(): Sequence<Triple<Point3D, Point3D, Double>> = asSequence()
    .flatMapIndexed { i, point ->
      this.drop(i + 1).asSequence().map { other ->
        Triple(point, other, point.distanceTo(other))
      }
    }.filterNot { it.first == it.second }
    .sortedBy { it.third }

  private fun Input.MultiLine.toJunctions() = map {
    Point3D.fromString(it)
  }

}

class CircuitFolder(
  junctions: Collection<Point3D>
) {

  private val circuits = junctions.map { mutableSetOf(it) }.toMutableSet()
  private var lastJunctionWallDistance: Long = 0

  fun fold(a: Point3D, b: Point3D): CircuitFolder {
    if (lastJunctionWallDistance > 0) return this

    // TODO This takes around 800Mb of allocations for part two, it could be improved
    // Have an array of circuit number for every junction, initialized with its index
    // Making a connection between a(n = 1) and b(n = 2) is simply changing b to b(n = 1)
    val circuitA = circuits.find { it.contains(a) }!!
    val circuitB = circuits.find { it.contains(b) }!!
    val fused = (circuitA + circuitB).toMutableSet()
    circuits.remove(circuitA)
    circuits.remove(circuitB)
    circuits.add(fused)


    if (circuits.size == 1) {
      lastJunctionWallDistance = a.x.toLong() * b.x
    }

    return this
  }

  fun circuitSizes(): Long = circuits.map { it.size }.sortedDescending().take(3).multiply().toLong()

  fun distanceFromWall(): Long = lastJunctionWallDistance

}

fun main() {
  Circuits().run()
}
