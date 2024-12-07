import java.nio.file.Files
import java.nio.file.Path

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}
data class Position(val row: Int, val col: Int)

fun main() {
    val area = Files.readAllLines(Path.of("input.txt"))
        .map { it.toCharArray() }
        .toTypedArray()
    val startPos = findStartPos(area)
    val looppos= mutableListOf<Position>()
    var loopCount = 0
    var currentDirection = Direction.UP
    val route = mutableListOf(startPos)
    while (true) {
        val currentPos = route.last()
        val nextPosition = when(currentDirection) {
            Direction.UP -> {
                 Position(currentPos.row - 1, currentPos.col)
            }
            Direction.DOWN -> {
                 Position(currentPos.row + 1, currentPos.col)
            }
            Direction.LEFT -> {
               Position(currentPos.row, currentPos.col - 1)
            }
            Direction.RIGHT -> {
               Position(currentPos.row, currentPos.col + 1)
            }
        }
        if (nextPosition.row < 0 || nextPosition.row >= area.size || nextPosition.col < 0 || nextPosition.col >= area[0].size) {
            break
        }
        if (area[nextPosition.row][nextPosition.col] != '#') {
            route.add(nextPosition)
            //check next position would cause loop
            val copyArea = area.toMutableList().map { it.toMutableList().toCharArray() }.toTypedArray()
            copyArea[nextPosition.row][nextPosition.col] = '#'
            println("Checking loop for $nextPosition")
            if(causesLoop(copyArea, startPos)) {
                looppos.add(nextPosition)
                loopCount++
            }
        }else {
            currentDirection = when(currentDirection) {
                Direction.UP -> Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.LEFT -> Direction.UP
            }
        }
    }
    looppos.forEach {
        area[it.row][it.col] = 'O'
    }
    area.forEach {
        println(it)
    }
    println(looppos.distinct().size)
}

fun causesLoop(area: Array<CharArray>, startPos: Position): Boolean {
    val route = mutableListOf(startPos)
    var currentDirection = Direction.UP
    val occurence = mutableMapOf<Position, Int>()
    while (true) {
        if(route.size> 10000) {
            println("loop detection sucks")
            return true
        }
        val currentPos = route.last()
        val nextPosition = when(currentDirection) {
            Direction.UP -> {
                Position(currentPos.row - 1, currentPos.col)
            }
            Direction.DOWN -> {
                Position(currentPos.row + 1, currentPos.col)
            }
            Direction.LEFT -> {
                Position(currentPos.row, currentPos.col - 1)
            }
            Direction.RIGHT -> {
                Position(currentPos.row, currentPos.col + 1)
            }
        }
        if (nextPosition.row < 0 || nextPosition.row >= area.size || nextPosition.col < 0 || nextPosition.col >= area[0].size) {
            break
        }
        if (area[nextPosition.row][nextPosition.col] != '#') {
            route.add(nextPosition)
            occurence[nextPosition] = occurence.getOrDefault(nextPosition, 0) + 1
            if(occurence[nextPosition]!! > 3) {
                return true
            }
        }else {
            currentDirection = when(currentDirection) {
                Direction.UP -> Direction.RIGHT
                Direction.RIGHT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.LEFT -> Direction.UP
            }
        }
    }
    return false
}

fun findStartPos(area: Array<CharArray>): Position {
    for (row in area.indices) {
        for (col in area[0].indices) {
            if (area[row][col] == '^') {
                return Position(row, col)
            }
        }
    }
    throw IllegalArgumentException("No start position found")
}
