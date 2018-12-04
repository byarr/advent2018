package com.brianyarr.advent2018.day4

import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.max
import kotlin.streams.toList

fun main(args: Array<String>) {
    part2()


}

fun parseShifts(): MutableList<Shift> {
    val lines = Files.lines(Paths.get("../input/day4.txt"))
            .sorted()
            .toList()

    val tsRegex =  Regex("""\[(\d+)-(\d+)-(\d+) (\d+):(\d+)\]""")
    val idRegex = Regex("""#\d+""")

    val shifts = mutableListOf<Shift>()

    var currentShift: Shift? = null
    for (line in lines) {
        val find = tsRegex.find(line)
        if (find == null) {
            continue
        }
        else {
            val min = find.groupValues.get(5).toInt()

            if (line.contains("begins")) {
                val idResult = idRegex.find(line)
                currentShift = Shift(idResult?.value?.substring(1)?.toInt()!!, mutableListOf())
                shifts.add(currentShift)
            } else {
                if (line.contains("wakes")) {
                    currentShift?.transitions?.add(Transition(min, false))
                } else {
                    currentShift?.transitions?.add(Transition(min, true))
                }
            }
        }

    }
    return shifts
}

fun part1() {

    val shifts = parseShifts()

    println(shifts)
    val maxSleep = shifts.asSequence()
            .groupBy({ it.id }, { it.minutesAsleep().count() })
            .mapValues { it.value.asSequence().sum() }
            .maxBy { it.value }

    val maxId = maxSleep?.key!!

    val maxBy = shifts.asSequence()
            .filter { it.id == maxId }
            .flatMap { it.minutesAsleep().asSequence() }
            .groupBy { it }
            .maxBy { it.value.size }


    println(maxId * maxBy?.key!!)

}

fun part2() {
    val shifts = parseShifts()

    val maxBy = shifts.asSequence()
            .groupBy { it.id }
            .map { Guard(it.key, it.value) }
            .maxBy { it.sleepiestMinute().second }


    val maxId = maxBy?.id!!
    val minute = maxBy?.sleepiestMinute()?.first!!
    println(maxId * minute)



}

fun mostSleepy(shifts: List<Shift>): Int {

    return shifts.asSequence()
            .flatMap { it.minutesAsleep().asSequence() }
            .groupBy { it }
            .mapValues { it.value.size }
            .maxBy { it.value }
            ?.key ?: -1

}

data class Guard(val id: Int, val shifts: List<Shift>) {
    fun sleepiestMinute(): Pair<Int, Int> {

        val maxMinute = shifts.asSequence()
                .flatMap { it.minutesAsleep().asSequence() }
                .groupBy { it }
                .maxBy { it.value.size }
        if (maxMinute == null) {
            return Pair(0,0);
        } else {
            return Pair(maxMinute.key, maxMinute.value.size)
        }


    }

}

data class Shift(val id: Int, val transitions: MutableList<Transition>) {
    fun minutesAsleep(): List<Int> {
        val mins = mutableListOf<Int>()
        var lastChange = 0
        var awake = true
        for (transition in transitions) {
            if (awake && transition.sleep) {
                awake = false


            } else if (!awake && !transition.sleep) {
                awake = true
                mins.addAll( lastChange until transition.time)
            }
            lastChange = transition.time
        }
        return mins
    }
}

data class Transition(val time: Int, val sleep: Boolean)