const val REDS_LIMIT = 12
const val GREENS_LIMIT = 13
const val BLUES_LIMIT = 14

class Game(s: String) {
    var id: Int = 0
    var sets: List<GameSet> = emptyList()

    init {
        id = s.split(":").first().filter { it.isDigit() }.toInt()

        sets =
            s.split(": ").last().split("; ").map { set ->
                val colors = set.split(", ")
                GameSet(
                    blue = colors.firstOrNull { it.contains("blue") }?.split(" ")?.first()?.toInt() ?: 0,
                    red = colors.firstOrNull { it.contains("red") }?.split(" ")?.first()?.toInt() ?: 0,
                    green = colors.firstOrNull { it.contains("green") }?.split(" ")?.first()?.toInt() ?: 0,
                )
            }
    }

    //    fun gameValue(): Int = if (isPossible()) id else 0
    fun gameValue(): Int {
        val value = if (isPossible()) id else 0
        "Game: $id, value: $value".println()
        return value
    }

    private fun isPossible(): Boolean =
        sets.none {
            it.blue > BLUES_LIMIT ||
                    it.red > REDS_LIMIT ||
                    it.green > GREENS_LIMIT
        }

    override fun toString() = "Game: $id, sets(${sets.count()}): $sets"

    fun findMinValues() {
        val biggestRed = sets.maxOf { it.red }
        val biggestBlue = sets.maxOf { it.blue }
        val biggestGreen = sets.maxOf { it.green }
        "Game: $id, min red's: $biggestRed, min blue's: $biggestBlue, min green's: $biggestGreen".println()
    }

    fun powerValue() : Int {
        val biggestRed = sets.maxOf { it.red }
        val biggestBlue = sets.maxOf { it.blue }
        val biggestGreen = sets.maxOf { it.green }
        return biggestRed * biggestBlue * biggestGreen
    }
}

fun List<Game>.totalValue() = sumOf { it.gameValue() }
fun List<Game>.totalPowerOfCubes() = sumOf { it.powerValue() }

data class GameSet(
    val blue: Int,
    val red: Int,
    val green: Int
)

fun main() {

    /**
     * Determine which games would have been possible if the bag had been
     * loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes.
     * What is the sum of the IDs of those games?
     */
    fun part1(input: List<String>): Int {
        val games: List<Game> = input.map { inputLine ->
            Game(inputLine)
        }
//        games.forEach { it.println() }
        return games.totalValue()
    }

    /**
     */
    fun part2(input: List<String>): Int {
        val games: List<Game> = input.map { inputLine ->
            Game(inputLine)
        }
        return games.totalPowerOfCubes()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    check(part1(input) == 2449)
    check(part2(input) == 63981)
//    part2(input).println()
}