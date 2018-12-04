package com.brianyarr.advent2018.day2

import com.sun.org.apache.xpath.internal.operations.Bool
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

fun part1() {
    val two = Files.lines(Paths.get("../input/day2.txt"))
            .map { Line(it) }
            .filter { it.exact2 }
            .count()

    val three = Files.lines(Paths.get("../input/day2.txt"))
            .map { Line(it) }
            .filter { it.exact3 }
            .count()

    println(two * three)
}

fun part2() {
    val lines = Files.lines(Paths.get("../input/day2.txt")).toList()

    for (i in 0 until lines.size - 1) {
        for (j in (i+1) until lines.size) {
            if (isSimilar(lines[i], lines[j])) {
                println(lines[i])
                println(lines[j])
                val toList = lines[i].asSequence().zip(lines[j].asSequence())
                        .filter { it.first == it.second }
                        .map { it.first }
                        .joinToString(separator = "")
                println(toList)
                return
            }

        }
    }


}

fun isSimilar(s: String, t: String): Boolean {
    return s.asSequence().zip(t.asSequence()).filter { it.first != it.second }.count() < 2
}

class Line(val l: String) {

    val exact2: Boolean
    val exact3: Boolean

    init {
        val byChar = l.asSequence().groupBy { it }
        exact2 = byChar.values.asSequence().any { it.size == 2 }
        exact3 = byChar.values.asSequence().any { it.size == 3 }

        l.asSequence().zip(l.asSequence()).filter { it.first != it.second }
    }

}

fun main(args: Array<String>) {
    part2()
}