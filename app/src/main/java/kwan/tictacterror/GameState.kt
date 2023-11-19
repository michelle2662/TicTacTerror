package kwan.tictacterror

data class GameState(
    val playerOScore: Int = 0,
    val playerXScore: Int = 0,
    val currentTurn:BoardCellValue = BoardCellValue.CROSS,
    val hasWon: Boolean = false,
    val board:Board = Board(),
) {
    fun playIJ( i: Int, j: Int) : GameState {
        val nextPlayer : BoardCellValue
        var newPlayerOScore = playerOScore
        var newPlayerXScore = playerXScore
        if (currentTurn == BoardCellValue.CIRCLE){
            nextPlayer = BoardCellValue.CROSS
            newPlayerOScore += calculateScore(currentTurn)
        }else {
            nextPlayer = BoardCellValue.CIRCLE
            newPlayerXScore += calculateScore(currentTurn)

        }
        return copy(board = board.makeMove(i,j,currentTurn), currentTurn = nextPlayer,
            playerOScore = newPlayerOScore, playerXScore = newPlayerXScore)
    }

    fun calculateScore(currentTurn: BoardCellValue): Int{
        // TODO
        return 0
    }

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
        0 -> Pair(0,0);
        1 -> Pair(0,3);
        2 -> Pair(0,6);
        3 -> Pair(3,0);
        4 -> Pair(3,3);
        5 -> Pair(3,6);
        6 -> Pair(6,0);
        7 -> Pair(6,3);
        8 -> Pair(6,6);
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
