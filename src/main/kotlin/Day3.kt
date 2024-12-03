import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val input = Files.readString(Path.of("input.txt"))

    var text = input
    var sum = 0
    var enabled = true
    while (true) {
        if (text.isEmpty()) break
        if (enabled) {
            val mulText = text.substringBefore("don't()")
            sum += mulSum(mulText)
            text = text.drop(mulText.length + "don't()".length)
            enabled = false
        } else {
            val noMulTet = text.substringBefore("do()")
            text = text.drop(noMulTet.length + "do()".length)
            enabled = true
        }
    }
    println(sum)

}

fun mulSum(input: String): Int {

    val regex = Regex("""mul\(\d+,\d+\)""")
    return regex.findAll(input).sumOf {
        val (a, b) = it.value.drop(4).dropLast(1).split(",")
        a.toInt() * b.toInt()
    }
}
