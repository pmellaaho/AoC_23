fun main() {

    /**
     * In this example, the calibration values of these four lines are 12, 38, 15, and 77.
     * Adding these together produces 142.
     */
    fun part1(input: List<String>): Int {
        var res = 0

        input.forEach { line ->
            val onlyDigits =
                line.filter { it.isDigit() }.let {
                    when {
                        it.length > 2 -> {
                            val s =
                                it.take(1) + it.takeLast(1)
                            s.toInt()
                        }

                        it.length == 1 -> {
                            val s = it + it
                            s.toInt()
                        }

                        else -> it.toInt()
                    }
                }
            res += onlyDigits
        }
        res.println()
        return res
    }


    fun digitFrom(s: String): String? {
        return when {
            s.startsWith("one") -> "1"
            s.startsWith("two") -> "2"
            s.startsWith("three") -> "3"
            s.startsWith("four") -> "4"
            s.startsWith("five") -> "5"
            s.startsWith("six") -> "6"
            s.startsWith("seven") -> "7"
            s.startsWith("eight") -> "8"
            s.startsWith("nine") -> "9"
            s.startsWith("zero") -> "0"
            else -> null
        }
    }


    fun findFirstDigit(chars: String): String {
        for (i in 0..<chars.lastIndex + 1) {
            if (chars[i].isDigit()) return chars[i].toString()
            else {
                val digit = digitFrom(chars.substring(i, chars.lastIndex))
                if (digit != null) return digit
            }
        }
        return "0"
    }

    fun findLastDigit(chars: String): String {
        for (i in chars.lastIndex downTo 0) {
            if (chars[i].isDigit()) return chars[i].toString()
            else {
                val ending = chars.substring(i, chars.lastIndex + 1)
                val digit = digitFrom(ending)
                if (digit != null) return digit
            }
        }
        return "0"
    }

    /**
     * In this example, the calibration values are 29, 83, 13, 24, 42, 14, and 76.
     * Adding these together produces 281.
     */
    fun part2(input: List<String>): Int {
        var res = 0

        input.forEach { line ->
            val first = findFirstDigit(line)
            val second = findLastDigit(line)

            val thisLine = (first + second).toInt()
            res += thisLine
        }
        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_2_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
//    part1(input).println()
    part2(input).println()
}