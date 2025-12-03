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
