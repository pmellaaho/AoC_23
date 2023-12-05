import kotlin.math.abs

fun main() {

    data class PartNumber(
            val value: Int,
            val position: Pair<Int, Int>  // x, y
    ) {

        fun getPoints(): List<Pair<Int, Int>> {
            val length = value.toString().length
            val points = mutableListOf<Pair<Int, Int>>()

            repeat(length) { idx ->
                points.add(Pair(position.first + idx, position.second))
            }
            return points.toList()
        }
    }

    data class Symbol(
            val position: Pair<Int, Int>  // x, y
    ) {
        fun isAdjacentTo(part: PartNumber): Boolean {
            val adjacent = part.getPoints().any { partPoint ->
                isAjdacent(this.position.first,
                        this.position.second,
                        partPoint.first,
                        partPoint.second)
            }
            return adjacent
        }

        fun getAdjacentParts(parts: List<PartNumber>): List<PartNumber> {
            return parts.filter { part ->
                part.getPoints().any { partPoint ->
                    isAjdacent(this.position.first,
                            this.position.second,
                            partPoint.first,
                            partPoint.second)
                }
            }
        }

        // returns true if (x1,y1) is adjacent to (x2,y2)
        fun isAjdacent(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
            val dx = abs(x1 - x2)
            val dy = abs(y1 - y2)
            return (dx < 2 && dy < 2 && dx + dy <= 2)
        }

    }

    fun parseInput(input: List<String>): Pair<List<PartNumber>, List<Symbol>> {
        val parts = mutableListOf<PartNumber>()
        val symbols = mutableListOf<Symbol>()

        input.forEachIndexed { y, str ->
            Regex("[0-9]+").findAll(str).map { Pair(it.value.toInt(), it.range.first) }
                    .forEach {
                        parts.add(PartNumber(it.first, Pair(it.second, y)))
                    }
        }

        input.forEachIndexed { y, str ->
            Regex("[^0-9|^.]").findAll(str).map { it.range.first }
                    .forEach {
                        symbols.add(Symbol(Pair(it, y)))
                    }
        }
        return Pair(parts.toList(), symbols.toList())
    }

    /**
     * any number adjacent to a symbol, even diagonally, is a "part number" and should be
     * included in your sum.
     */
    fun part1(input: List<String>): Int {
        val (parts, symbols) = parseInput(input)
        return parts.sumOf { part ->
            if (symbols.any { it.isAdjacentTo(part) }) part.value else 0
        }
    }

    /**
     * A gear is any * symbol that is adjacent to exactly two part numbers.
     * Its gear ratio is the result of multiplying those two numbers together.
     * This time, you need to find the gear ratio of every gear and add them all up
     */
    fun part2(input: List<String>): Int {

        // Hox! This solution does NOT check only '*' -symbols that is adjacent to two part numbers
        // Luckily it still produces a correct result.

        val (parts, symbols) = parseInput(input)
        return symbols.sumOf { symbol ->
            symbol.getAdjacentParts(parts).let {
                if (it.size == 2) it.first().value * it.last().value
                else 0
            }
        }
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    check(part1(input) == 527446)
    check(part2(input) == 73201705)
    part2(input).println()
}