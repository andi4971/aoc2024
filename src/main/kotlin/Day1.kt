import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs

fun main() {
    val listA = mutableListOf<Int>()
    val listB = mutableListOf<Int>()
    Files.readAllLines(Path.of("input.txt")).map { line ->
        val entry = line.split("   ")
        listA += entry.first().toInt()
        listB += entry.last().toInt()
    }
    listA.sort()
    listB.sort()

    var sum = 0
    while(listA.isNotEmpty()) {
        val a = listA.removeAt(0)
        val b = listB.removeAt(0)
        sum += abs(a - b)
    }
    println(sum)

}
