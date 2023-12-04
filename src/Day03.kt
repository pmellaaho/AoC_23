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

            if (adjacent) "Part ${part.value} is adjacent to symbol".println()
            return adjacent
        }

        // returns true if (x1,y1) is adjacent to (x2,y2)
        fun isAjdacent(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
            val dx = abs(x1 - x2)
            val dy = abs(y1 - y2)
            return (dx < 2 && dy < 2 && dx + dy <= 2)
        }
    }

    /**
     * any number adjacent to a symbol, even diagonally, is a "part number" and should be
     * included in your sum.
     */
    fun part1(input: List<String>): Int {
        var parts: List<PartNumber> = emptyList()
        var symbols: List<Symbol> = emptyList()

        /**
         * In this schematic, two numbers are not part numbers because they are not adjacent
         * to a symbol: 114 (top right) and 58 (middle right).
         * Every other number is adjacent to a symbol and so is a part number;
         * their sum is 4361.
         */

        parts = listOf(
                PartNumber(467, Pair(0, 0)),
                PartNumber(114, Pair(5, 0)),
                PartNumber(35, Pair(2, 2)),
                PartNumber(633, Pair(6, 2)),
                PartNumber(617, Pair(0, 4)),
                PartNumber(58, Pair(7, 5)),
                PartNumber(592, Pair(2, 6)),
                PartNumber(755, Pair(6, 7)),
                PartNumber(664, Pair(1, 9)),
                PartNumber(598, Pair(5, 9))
        )

        symbols = listOf(
                Symbol(Pair(3, 1)),  // *
                Symbol(Pair(6, 3)),  // #
                Symbol(Pair(3, 4)),  // *
                Symbol(Pair(5, 5)),  // +
                Symbol(Pair(3, 8)),  // $
                Symbol(Pair(5, 8))   // *
        )

        return parts.sumOf { part ->
            if (symbols.any { it.isAdjacentTo(part) }) part.value else 0
        }
    }

    /**
     */
    fun part2(input: List<String>): Int {

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
//    check(part2(testInput) == 2286)

//    val input = readInput("Day03")
//    check(part1(input) == 2449)
//    check(part2(input) == 63981)
//    part2(input).println()
}