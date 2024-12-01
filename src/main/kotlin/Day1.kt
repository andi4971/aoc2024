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

    var sum = 0
    while(listA.isNotEmpty()) {
        val a = listA.removeFirst()
        listB.count { it == a }.let { sum += it * a }
    }
    println(sum)

}
