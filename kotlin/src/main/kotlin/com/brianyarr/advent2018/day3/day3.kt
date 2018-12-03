package com.brianyarr.advent2018.day3

import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Function
import java.util.stream.Collectors
import java.util.stream.Collectors.groupingBy
import kotlin.streams.asSequence

data class Claim(val id: Int, val leftEdge: Int, val topEdge: Int, val wdith: Int, val height: Int) {

    fun pointsCovered(): List<Point> {
        val result = mutableListOf<Point>()
        for (i in 1..wdith) {
            for (j in 1..height) {
                result.add(Point(leftEdge + i, topEdge + j))
            }
        }
        return result
    }

    fun hasPointIn(points: Set<Point>): Boolean {
        return pointsCovered().asSequence().filter { points.contains(it) }.any()
    }

}

fun parseClain(line: String): Claim? {
    val all = Regex("\\d+").findAll(line)
    val matches = all.toList()
    if (matches.size < 4 ) {
        return null
    }
    return Claim(matches[0].value.toInt(), matches[1].value.toInt(), matches[2].value.toInt(), matches[3].value.toInt(), matches[4].value.toInt())
}

data class Point(val x: Int, val y: Int)


fun part1() {
    val count = Files.lines(Paths.get("../input/day3.txt"))
            .map { parseClain(it) }
            .filter { it != null }
            .flatMap { it?.pointsCovered()?.stream() }
            .asSequence()
            .groupBy { it }
            .asSequence()
            .filter { it.value.size > 1 }
            .count()
    println("${count}")
}


fun part2() {
    val overlappedPoints = Files.lines(Paths.get("../input/day3.txt"))
            .map { parseClain(it) }
            .filter { it != null }
            .flatMap { it?.pointsCovered()?.stream() }
            .asSequence()
            .groupBy { it }
            .asSequence()
            .filter { it.value.size > 1 }
            .map { it.key }
            .toSet()

    println(overlappedPoints.size)

    Files.lines(Paths.get("../input/day3.txt"))
            .map { parseClain(it) }
            .asSequence()
            .filterNotNull()
            .filter { !it.hasPointIn(overlappedPoints) }
            .forEach { println(it) }

}

fun main(args: Array<String>) {

    part2()


}