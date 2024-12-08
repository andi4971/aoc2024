import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val antennas = mutableMapOf<Char, MutableList<Position>>()
    val antiNodes = mutableMapOf<Char, MutableSet<Position>>()
    val map = Files.readAllLines(Path.of("input.txt"))
        .map { it.toCharArray() }.toTypedArray()

    map.forEachIndexed { row, chars ->
        chars.forEachIndexed { col, char ->
            if (char != '.') {
                antennas.computeIfAbsent(char) { mutableListOf() }.add(Position(row, col))
            }
        }
    }

    antennas.forEach { (antenna, positions) ->
        positions.forEach { sourcePos ->
            positions.filter { it != sourcePos }
                .forEach { destPos ->


                    var pointToMirror = destPos
                    var mirrorSource = sourcePos
                    while(pointToMirror.col >= 0 && pointToMirror.col < map[0].size && pointToMirror.row >= 0 && pointToMirror.row < map.size) {
                        antiNodes.computeIfAbsent(antenna) { mutableSetOf() }.add(mirrorSource)
                        val temp = mirrorSource
                        mirrorSource = mirrorPoint(mirrorSource, pointToMirror)
                        pointToMirror = temp

                    }
                }
        }
    }
    antiNodes.values.flatten()
        .filter { it.row >= 0 && it.row < map.size && it.col >= 0 && it.col < map[0].size }
        .distinct()
        //.filter { map[it.row][it.col] == '.' }
        .count().let { println(it) }

}

fun mirrorPoint(sourcePos: Position, destPos: Position): Position {
    val directionRow = destPos.row - sourcePos.row
    val directionCol = destPos.col - sourcePos.col
    val mirroredRow = sourcePos.row - directionRow
    val mirroredCol = sourcePos.col - directionCol
    return Position(mirroredRow, mirroredCol)
}
