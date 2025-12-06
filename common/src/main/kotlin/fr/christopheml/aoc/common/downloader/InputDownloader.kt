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

package fr.christopheml.aoc.common.downloader

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.cookie
import io.ktor.client.request.request
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.headers
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.system.exitProcess

suspend fun main() {
  val year = LocalDate.now().year
  val inputDir = Path(System.getProperty("user.dir"))
    .resolve("$year/src/main/resources/inputs")

  if (!inputDir.exists()) {
    System.err.println("Inputs directory does not exist: $inputDir")
    exitProcess(1)
  }

  val (day, destination) = nextInputFile(year, inputDir)
  val baseUrl = "https://adventofcode.com/$year/day/$day"

  val session = Path.of(System.getProperty("user.dir"))
  .resolve(".env")
  .toFile()
  .readLines()
  .first { it.startsWith("AOC_SESSION=") }
  .substringAfter("=")

  val client = HttpClient(CIO) {
    install(Logging) {
      logger = Logger.SIMPLE
      level = LogLevel.INFO
    }
  }

  client.use { client ->
    val response: HttpResponse = client.request("$baseUrl/input") {
      cookie("session", session)
      headers {
        append(HttpHeaders.Referrer, baseUrl)
        append(HttpHeaders.UserAgent, "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:145.0) Ktor/20100101 Firefox/145.0")
      }
    }

    if (response.status != HttpStatusCode.OK) {
      System.err.println("Error: ${response.status} ${response.bodyAsText()}")
      exitProcess(1)
    } else {
      Files.write(destination.toPath(), response.readRawBytes())
    }
  }

}

private fun nextInputFile(year: Int, directory: Path): Pair<Int, File> =
  (1..(if (year < 2025) 25 else 12))
    .mapIndexed { i, day -> Pair(i + 1, directory.resolve("day%02d.txt".format(day)).toFile()) }
    .first { !it.second.exists() }
