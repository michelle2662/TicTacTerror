package kwan.tictacterror

import android.util.Log

data class Point(val x: Int, val y: Int)
typealias Line = Set<Point>

fun Line.containsPoint(i : Int, j: Int): Boolean = contains(Point(i,j))

data class GameState(
    val playerOScore: Int = 0,
    val playerXScore: Int = 0,
    val currentTurn:BoardCellValue = BoardCellValue.CROSS,
    val linesCreated : List<Line> = emptyList(),
    val hasWon: Boolean = false,
    val board:Board = Board(),
) {
    fun playIJ(i: Int, j: Int) : GameState {
        val nextPlayer : BoardCellValue
        val newBoard = board.makeMove(i,j,currentTurn)
        var newPlayerOScore = playerOScore
        var newPlayerXScore = playerXScore
        val newLines = mutableListOf<Line>()
        val newScore = calculateLinesAndScore(newBoard.board, newLines, i, j)
        if (currentTurn == BoardCellValue.CIRCLE) {
            nextPlayer = BoardCellValue.CROSS
            newPlayerOScore += newScore
        } else {
            nextPlayer = BoardCellValue.CIRCLE
            newPlayerXScore += newScore
        }
        return copy(
            board = newBoard,
            currentTurn = nextPlayer,
            linesCreated = newLines,
            playerOScore = newPlayerOScore,
            playerXScore = newPlayerXScore
        )
    }
}


private fun Line.toScore(): Int {
    return 2 * size - 5
}


fun calculateLinesAndScore(
    board: Array<Array<BoardCellValue>>,
    outLines: MutableList<Line>,
    i: Int,
    j: Int
): Int {
    val player = board[i][j]
    check(player != BoardCellValue.NONE) {
        "Move has not been played on board yet"
    }
    val topLeftDiagonal = calculateLine(board, player, i, j, -1, -1)
    val topRightDiagonal = calculateLine(board, player, i, j, -1, 1)
    val verticalLine = calculateLine(board, player, i, j, 1, 0)
    val horizontalLine = calculateLine(board, player, i, j, 0, 1)

    Log.d("TTT", "topLeftDiagonal: $topLeftDiagonal")
    Log.d("TTT", "topRightDiagonal: $topRightDiagonal")
    Log.d("TTT", "verticalLine: $verticalLine")
    Log.d("TTT", "horizontalLine: $horizontalLine")

    val allLines = listOfNotNull(
        topLeftDiagonal,
        topRightDiagonal,
        verticalLine,
        horizontalLine
    )
    outLines.addAll(allLines)
    return allLines.sumOf { it.toScore() }
}

fun calculateLine(
    board: Array<Array<BoardCellValue>>,
    player: BoardCellValue,
    i: Int,
    j: Int,
    rowIncrement: Int,
    colIncrement: Int
): Line? {
    // calculate length of line Top Left to Bottom Right
    val line = mutableSetOf<Point>()
    var x = i
    var y = j
    while (x >= 0 && y >= 0 && x < 9 && y < 9 && board[x][y] == player) {
        line.add(Point(x, y))
        x += rowIncrement
        y += colIncrement
    }
    x = i
    y = j
    while (x >= 0 && y >= 0 && x < 9 && y < 9 && board[x][y] == player) {
        line.add(Point(x, y))
        x += -rowIncrement
        y += -colIncrement
    }

    return line.takeIf { it.size >= 3 }
}



data class Board(
    val board: Array<Array<BoardCellValue>> = Array(9, {i -> Array(9, {j -> BoardCellValue.NONE})}),
    var activeBoard: Int = 0

) {
    fun emptySpace(i:Int, j:Int):Boolean{
        if (board[i][j] != BoardCellValue.NONE){
            return false
        }
        return true
    }
    fun makeMove(row:Int, col:Int, currentTurn: BoardCellValue) : Board {
        val newBoard : Array<Array<BoardCellValue>> = Array(9) { i ->
            Array(
                9
            ) { j -> board[i][j] }
        }

        newBoard[row][col] = currentTurn

        if (advanceActiveBoard(newBoard, activeBoard)) {
            if (activeBoard == 2){
                activeBoard = 5
            } else if (activeBoard == 5){
                activeBoard = 8
            } else if (activeBoard > 6 && activeBoard <= 8){
                activeBoard -=1
            }else if (activeBoard == 6){
                activeBoard = 3
            }else if (activeBoard == 3){
                activeBoard = 4
            } else {
                activeBoard+=1
            }
        }
        return Board(newBoard, activeBoard)

    }

    fun isPlayable(i:Int, j:Int) : Boolean {
        val index :Int = (i/3 * 3) + (j/3)

        return activeBoard == index && emptySpace(i,j)
    }
}

private fun advanceActiveBoard(board:Array<Array<BoardCellValue>>, activeBoard: Int): Boolean{
    val (row, col) = when (activeBoard){
        0 -> Pair(0,0)
        1 -> Pair(0,3)
        2 -> Pair(0,6)
        3 -> Pair(3,0)
        4 -> Pair(3,3)
        5 -> Pair(3,6)
        6 -> Pair(6,0)
        7 -> Pair(6,3)
        8 -> Pair(6,6)
        else -> {
            error("Invalid active board")
        }
    }

    //vertical first column
    if (board[row][col] == board[row+1][col] && board[row+1][col] == board[row+2][col] && board[row][col] != BoardCellValue.NONE){
        return true
    }

    //vertical second column
    if (board[row][col+1] == board[row+1][col+1] && board[row+1][col+1] == board[row+2][col+1] && board[row+2][col + 1] != BoardCellValue.NONE){
        return true
    }

    //vertical third column
    if (board[row][col+2] == board[row+1][col+2] && board[row+1][col+2] == board[row+2][col+2] && board[row+2][col+2] != BoardCellValue.NONE){
        return true
    }

    //horizontal first row
    if (board[row][col] == board[row][col+1] && board[row][col+1] == board[row][col+2] && board[row][col+1] != BoardCellValue.NONE){
        return true
    }

    //horizontal second row
    if (board[row+1][col] == board[row+1][col+1] && board[row+1][col+1] == board[row+1 ][col+2] && board[row+ 1][col + 1] != BoardCellValue.NONE){
        return true
    }

    //horizontal third row
    if (board[row+2][col] == board[row+2][col+1] && board[row+2][col+1] == board[row+2 ][col+2] && board[row+ 2][col] != BoardCellValue.NONE){
        return true
    }

    //diagnoal left to right
    if (board[row][col] == board[row+1][col+1] && board[row+1][col+1] == board[row+2 ][col+2] && board[row][col] != BoardCellValue.NONE){
        return true
    }

    //diagnoal right to left
    if (board[row][col+2] == board[row+1][col+1] && board[row+1][col+1] == board[row+2 ][col] && board[row+ 2][col] != BoardCellValue.NONE){
        return true
    }

    //check if active board is filled
    var count = 0
    for (i in  row .. row+2){
        for (j in col..col+2) {
            if (board[i][j] == BoardCellValue.NONE) {
                return false
            }else {
                count++
            }
        }
    }

    if (count == 9) {
        return true
    }else {
        return false
    }

}



enum class BoardCellValue{
    CIRCLE, CROSS, NONE
}

enum class VictoryType{

}
