import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val lines = Files.readAllLines(Path.of("input.txt"))
    val rules = lines.takeWhile { it.isNotBlank() }
        .map { it.split("|").let { it.first().toInt() to it.last().toInt() } }
    val manuals = lines.drop(rules.size + 1)
        .map { it.split(",").map { it.toInt() } }
        .mapNotNull {
            val curr = it.toMutableList()
            var changed = false
            val rulesToCheck = rules.toMutableList()
            while (rulesToCheck.isNotEmpty()) {
                val rule = rulesToCheck.removeFirst()
                if (!checkValid(curr, rule)) {
                    val leftIndex = curr.indexOf(rule.first)
                    val rightIndex = curr.indexOf(rule.second)
                    curr[leftIndex] = rule.second
                    curr[rightIndex] = rule.first
                    changed = true
                    rulesToCheck.clear()
                    rulesToCheck.addAll(rules)
                }
            }
            if(changed) {
                curr
            }else null
        }.sumOf { it.elementAt(it.size/2) }
    println(manuals)
}

fun checkValid(manual: List<Int>, rule: Pair<Int, Int>): Boolean {
    val (left, right) = rule
    if(!manual.containsAll(listOf(left,right))) {
        return true
    }
    val leftIndex = manual.indexOf(left)
    val rightIndex = manual.indexOf(right)
    return leftIndex < rightIndex
}
