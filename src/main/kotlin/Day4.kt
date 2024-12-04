import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val arr = Files.readAllLines(Path.of("input.txt"))
        .map { it.toCharArray() }
        .toTypedArray()
    var count = 0
    for (row in arr.indices) {
        for (col in arr[0].indices) {
            val curr = arr[row][col]
            if (curr != 'A') continue

            val str1 = arr.getOrNull(row - 1)?.getOrNull(col - 1)?.let { topLeft ->
                var currString = topLeft + "A"
                arr.getOrNull(row + 1)?.getOrNull(col + 1)?.let { currString + it }
            }

            val str2 = arr.getOrNull(row - 1)?.getOrNull(col+1)?.let { topRight ->
                var currString = topRight + "A"
                arr.getOrNull(row + 1)?.getOrNull(col - 1)?.let { currString + it }
            }

            if ((str1 == "MAS" || str1 == "SAM") && (str2 == "MAS" || str2 == "SAM")) {
                count++
            }
        }
    }
    println(count)
}

fun getEastString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    for (currCol in col until col + 4) {
        if (currCol >= arr[0].size) break
        currString += arr[row][currCol]
    }
    return currString
}

fun getSouthEastString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    var currRow = row
    for (currCol in col until col + 4) {
        if (currCol >= arr[0].size || currRow > arr.lastIndex) break
        currString += arr[currRow][currCol]
        currRow++
    }
    return currString
}

fun getSouthString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    for (currRow in row until row + 4) {
        if (currRow > arr.lastIndex) break
        currString += arr[currRow][col]
    }
    return currString
}

fun getSouthWestString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    var currRow = row
    for (currCol in col downTo col - 3) {
        if (currCol < 0 || currRow > arr.lastIndex) break
        currString += arr[currRow][currCol]
        currRow++
    }
    return currString
}

fun getWestString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    for (currCol in col downTo col - 3) {
        if (currCol < 0) break
        currString += arr[row][currCol]
    }
    return currString
}

fun getNorthWestString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    var currRow = row
    for (currCol in col downTo col - 3) {
        if (currCol < 0 || currRow < 0) break
        currString += arr[currRow][currCol]
        currRow--
    }
    return currString
}

fun getNorthString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    for (currRow in row downTo row - 3) {
        if (currRow < 0) break
        currString += arr[currRow][col]
    }
    return currString
}

fun getNorthEastString(row: Int, col: Int, arr: Array<CharArray>): String {
    var currString = ""
    var currRow = row
    for (currCol in col until col + 4) {
        if (currCol >= arr[0].size || currRow < 0) break
        currString += arr[currRow][currCol]
        currRow--
    }
    return currString
}
