import java.nio.file.Files
import java.nio.file.Path

fun main() {
    Files.readAllLines(Path.of("input.txt"))
        .map {
            val split = it.split(":")
            val res = split.first().toLong()
            val operators = split.last().trim().split(" ").map { it.toLong() }
            res to operators
        }
        .filter { (res, operators) ->
            val results = calculate(operators.drop(1), operators.first())
            results.contains(res)
        }.sumOf { it.first }
        .let { println(it) }
}

fun calculate(operators: List<Long>, currentRes: Long): List<Long> {
    if (operators.isEmpty()) {
        return listOf(currentRes)
    }
    val nextOperator = operators.first()
    val possibleResults = listOf(
        calculate(operators.drop(1), currentRes + nextOperator),
        calculate(operators.drop(1), currentRes * nextOperator),
        calculate(operators.drop(1), (currentRes.toString() + nextOperator.toString()).toLong()),
    ).flatten()
    return possibleResults
}
