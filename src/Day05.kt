data class Block(val src: Long, val dst: Long, val length: Long)
data class SeedsMap(val from: String, val to: String, val blocks: List<Block>)

fun main() {

    fun part1(input: List<String>): Long {
        val seeds = input.first().let { str ->
            Regex("\\d+").findAll(str).map { it.value.toLong() }
        } //.toList()
        "Nbr of seeds: ${seeds.count()}".println()

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
     *
     */
    fun part2(input: List<String>): Long {
        val seeds = input.first().let { str ->
            Regex("\\d+").findAll(str).map { it.value.toLong() }
        }.toList().chunked(2).map {
            it.first()..it.first() + it.last()
        }
        seeds.println()

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
                    // dst and src must be switched when going from location to seed
                    Block(dst, src, length)
                }
                SeedsMap(to, from, blocks)
            }.reversed()

        var location = 0L // 6400L
        var seed: Long = 0L
        while (seeds.none {
                it.contains(seed)
            }
        ) {
            "no seed found for location: $location".println()
            location += 1
            seed = mapList.findSeedForLocation(location)
        }

        "Seed: $seed found for location: $location".println()
        return location
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
//    check(part1(input) == 910845529L)
//    check(part2(input) == 77435348L)
//    part2(input).println()
}

fun List<SeedsMap>.findSeedForLocation(location: Long) =
    fold(location) { acc, map ->
        val mapped = acc.mapToValue(map)
        mapped
    }


private fun Long.mapToValue(input: SeedsMap): Long {
    input.blocks.forEach { block ->
        val sourceRange = block.src..block.src + block.length

        if (sourceRange.contains(this)) {
            return block.dst + (this - block.src)
//            return block.dst + sourceRange.indexOf(this)
        }
    }
    return this
}
