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

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RangeExtensionsTest {

  @Nested
  inner class Overlaps {

    @Test
    fun `when ranges are disjointed they should not overlap`() {
      10L.rangeTo(20L) shouldNotOverlap 30L.rangeTo(40L)
    }

    @Test
    fun `when ranges are not disjointed they should overlap`() {
      10.rangeTo(20) shouldOverlap 15.rangeTo(40)
    }

    @Test
    fun `when a range contains another they should overlap`() {
      0.0.rangeTo(7.5) shouldOverlap 3.0.rangeTo(6.0)
    }

    @Test
    fun `when ranges are identical they should overlap`() {
      'A'.rangeTo('Z') shouldOverlap 'A'.rangeTo('Z')
    }

  }

  @Nested
  inner class Contains {

    @Test
    fun `when ranges are disjointed none should contain they other`() {
      10L.rangeTo(20L) shouldNotContain 30L.rangeTo(40L)
      30L.rangeTo(40L) shouldNotContain 10L.rangeTo(20L)
    }

    @Test
    fun `when a range is fully enclosing another it should contain the other`() {
      10.rangeTo(20) shouldContain 12.rangeTo(15)
    }

    @Test
    fun `when a range is fully enclosed in another it should not contain the other`() {
      3.7.rangeTo(6.2) shouldNotContain 1.2.rangeTo(8.9)
    }

    @Test
    fun `when ranges are identical they should contain each other`() {
      'A'.rangeTo('Z') shouldContain 'A'.rangeTo('Z')
    }

  }

  @Nested
  inner class Merge {

    @Test
    fun `disjointed ranges can't merge`() {
      shouldThrow<IllegalArgumentException> {
        10L.rangeTo(20L).merge(30L.rangeTo(40L))
      }
    }

    @Test
    fun `merging with an enclosed range should produce the original range`() {
      10.rangeTo(20).merge(12.rangeTo(14)) shouldBe 10.rangeTo(20)
    }

    @Test
    fun `merging with an enclosing range should produce the enclosing range`() {
      2.3.rangeTo(3.1).merge(1.0.rangeTo(9.7)) shouldBe 1.0.rangeTo(9.7)
    }

    @Test
    fun `merge should be bijective`() {
      'A'.rangeTo('F').merge('C'.rangeTo('G')) shouldBe 'A'.rangeTo('G')
      'C'.rangeTo('G').merge('A'.rangeTo('F')) shouldBe 'A'.rangeTo('G')
    }

  }

  private infix fun <T : Comparable<T>> ClosedRange<T>.shouldOverlap(other: ClosedRange<T>) {
    this.overlaps(other).shouldBeTrue()
    other.overlaps(this).shouldBeTrue()
  }

  private infix fun <T : Comparable<T>> ClosedRange<T>.shouldNotOverlap(other: ClosedRange<T>) {
    this.overlaps(other).shouldBeFalse()
    other.overlaps(this).shouldBeFalse()
  }

  private infix fun <T : Comparable<T>> ClosedRange<T>.shouldContain(other: ClosedRange<T>) {
    this.contains(other).shouldBeTrue()
  }

  private infix fun <T : Comparable<T>> ClosedRange<T>.shouldNotContain(other: ClosedRange<T>) {
    this.contains(other).shouldBeFalse()
  }

}
