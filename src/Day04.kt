data class Card(
        val winners: List<Int>,
        val playerNumbers: List<Int>,

        ) {
    private var copies: Int = 1

    fun addCopy() = copies++
    fun getCopies() = copies
}

fun Card.calculatePoints(): Int =
        playerNumbers.fold(0) { sum, element ->
            if (winners.any { winner ->
                        element == winner
                    }
            ) {
                if (sum == 0) 1
                else sum * 2
            } else sum
        }

fun Card.calculateMatches(): Int =
    winners.count { it in playerNumbers }

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
                                    .forEach { s ->
                                        add(s.trim().toInt())
                                    }
                        }
                )
                )
            }
        }
    }
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
        val cards = parseInput(input).toMutableList()

        cards.forEachIndexed { index, card ->
            val matches = card.calculateMatches()

            repeat(card.getCopies()) {
                repeat(matches) { times ->
                    val cardIdx = index + times + 1

//                    "Card ${cardIdx + 1}: Add copy".println()
                    cards[cardIdx].addCopy()
                }
            }
        }

        val scratchcards = cards.sumOf { it.getCopies() }
        return scratchcards
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    check(part1(input) == 26218)
    check(part2(input) == 9997537)
//    part2(input).println()
}