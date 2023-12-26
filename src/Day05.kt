import java.lang.Exception
import java.util.InputMismatchException
import kotlin.math.cbrt

data class Block(val src: Long, val dst: Long, val length: Long)
data class SeedsMap(val from: String, val to: String, val blocks: List<Block>)

fun main() {

    /**
     * In this example, the calibration values of these four lines are 12, 38, 15, and 77.
     * Adding these together produces 142.
     */
    fun part1(input: List<String>): Long {

        val seeds = input.first().let { str ->
            Regex("\\d+").findAll(str).map { it.value.toLong() }
        }.toList()

        val mapList =
                input.drop(2).fold(mutableListOf(mutableListOf<String>())) { acc, string ->
                    if (string.isBlank()) {
                        acc.add(mutableListOf<String>())
                    } else {
                        acc.last().add(string)
                    }
                    acc
                }.map {
                    val (from, _, to) = it.first().split("-", " ")
                    val blocks = it.drop(1).map {
                        val (dst, src, length) = it.split(" ").map { it.toLong() }
                        Block(src, dst, length)
                    }
                    SeedsMap(from, to, blocks)
                }

        return seeds.minOf { seed ->
            mapList.fold<SeedsMap, Long>(seed) { acc, map ->
                val mapped = acc.mapToValue(map)
                mapped
            }
        }
    }

    /**
     * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76.
     * Adding these together produces 281.
     */
    fun part2(input: List<String>): Int {
        var res = 0
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
//    check(part1(testInput) == 35L)

    val input = readInput("Day05")
        part1(input).println()
//    check(part1(input) == 910845529L)
//    part2(input).println()
}

private fun Long.mapToValue(input: SeedsMap): Long {
    input.blocks.forEach { block ->
        val sourceRange = block.src..block.src + block.length
        if (sourceRange.contains(this)) {
            for ((index, value) in sourceRange.withIndex()) {
                if (value == this) {
                    return block.dst + index
                }
            }
        }
    }
    return this
}
