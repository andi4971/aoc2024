import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {
    val (valid, invalid) = Files.readAllLines(Path.of("input.txt"))
        .map { line -> line.split(" ").map { it.toInt() } }
        .partition { level ->
            checkValid(level)
        }
    val fixedInvalid = invalid.map { level ->
        for(i in 0..level.lastIndex) {
            val listWithoutI = level.toMutableList()
            listWithoutI.removeAt(i)
            if (checkValid(listWithoutI)) {
                return@map 1
            }
        }
        return@map 0
    }.sum()

    println(valid.count() + fixedInvalid)
}

private fun checkValid(level: List<Int>): Boolean {
    if (level.sorted() != level && level.sortedDescending() != level) {
        return false
    }
    for (i in 0..<level.lastIndex) {
        val cur = level[i]
        val next = level[i + 1]
        val dif = abs(cur - next)
        if (dif < 1 || dif > 3) {
            return false

        }
    }
    return true
}
