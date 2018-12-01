package com.brianyarr.advent2018

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

class RepeatingIterator(val items: List<Int>) : Iterator<Int> {

    var iter: Iterator<Int> = items.iterator()

    override fun hasNext(): Boolean {
        return true
    }

    override fun next(): Int {
        if (!iter.hasNext()) {
            iter = items.iterator()
        }
        return iter.next()
    }

}


fun part1() {
    val sum = Files.lines(Paths.get("../input/day1.txt"))
            .mapToInt(Integer::parseInt)
            .sum()
    println(sum)
}

fun part2() {
    val deltas = Files.lines(Paths.get("../input/day1.txt"))
            .mapToInt(Integer::parseInt)
            .toList()
    val frequenciesSeen = mutableSetOf<Int>()
    var freq: Int  = 0
    frequenciesSeen.add(freq)
    for (d in RepeatingIterator(deltas)) {
        freq += d
        val add = frequenciesSeen.add(freq)
        if (!add) {
            println(freq)
            return
        }

    }



}

fun main(args: Array<String>) {
    part2()
}