import kotlin.math.pow

data class Card(
        val winners: List<Int>,
        val playerNumbers: List<Int>
)

fun Card.calculatePoints(): Int =
        playerNumbers.fold(0) { sum, element ->
            winners.firstOrNull { winner ->
                element == winner
            }?.let {
                if (sum == 0) 1
                else sum * 2
            } ?: sum
        }

fun parseInput(input: List<String>): List<Card> {
    return buildList {
        input.forEach { row ->
            row.split(": ").last().split("| ").let {
                add(Card(
                        winners = buildList<Int> {
                            it.first()
                                    .replace("  ", " ")
                                    .split(" ")
                                    .filter { it.isNotEmpty() }
                                    .forEach { s ->
                                        add(s.trim().toInt())
                                    }
                        },
                        playerNumbers = buildList<Int> {
                            it.last()
                                    .replace("  ", " ")
                                    .split(" ")
                                    .filter { it.isNotEmpty() }                                    
                                    .forEach() { s ->
                                        add(s.trim().toInt())
                                    }
                        }
                )
                )
            }
        }
    }
}

fun geteInput(): List<Card> {
    return listOf(
            Card(
                    winners = listOf(41, 48, 83, 86, 17),
                    playerNumbers = listOf(83, 86, 6, 31, 17, 9, 48, 53)
            ),

            Card(
                    winners = listOf(13, 32, 20, 16, 61),
                    playerNumbers = listOf(61, 30, 68, 82, 17, 32, 24, 19)
            ),

            Card(
                    winners = listOf(1, 21, 53, 59, 44),
                    playerNumbers = listOf(69, 82, 63, 72, 16, 21, 14, 1)
            ),

            Card(
                    winners = listOf(41, 92, 73, 84, 69),
                    playerNumbers = listOf(59, 84, 76, 51, 58, 5, 54, 83)
            ),

            Card(
                    winners = listOf(87, 83, 26, 28, 32),
                    playerNumbers = listOf(88, 30, 70, 12, 93, 22, 82, 36)
            ),

            Card(
                    winners = listOf(31, 18, 13, 56, 72),
                    playerNumbers = listOf(74, 77, 10, 23, 35, 67, 36, 11)
            )
    )
}


fun main() {

    /**
     * you have to figure out which of the numbers you have appear in the list
     * of winning numbers. The first match makes the card worth one point and each match
     *  after the first doubles the point value of that card.
     */
    fun part1(input: List<String>): Int {
        val cards: List<Card> = parseInput(input)
        val points = cards.sumOf { it.calculatePoints() }
        return points
    }

    /**
     */
    fun part2(input: List<String>): Int {
        val cards: List<Card> = parseInput(input)
        
        
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    check(part1(input) == 26218)
//    check(part2(input) == 63981)
//    part2(input).println()
}