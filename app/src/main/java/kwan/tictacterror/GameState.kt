package kwan.tictacterror

data class GameState(
    val playerOScore: Int = 0,
    val playerXScore: Int = 0,
    //val drawCount: Int = 0,
    //val hintText: String = "Player 'O' turn",
    val currentTurn:BoardCellValue = BoardCellValue.CROSS,
    val hasWon: Boolean = false,
    //val victoryCount: Int = 0,
    val board:Board = Board(),
    //val victoryType: VictoryType
) {
    fun playIJ( i: Int, j: Int) : GameState {
        val nextPlayer : BoardCellValue
        if (currentTurn == BoardCellValue.CIRCLE){
            nextPlayer = BoardCellValue.CROSS
        }else {
            nextPlayer = BoardCellValue.CIRCLE
        }
        return copy(board = board.makeMove(i,j,currentTurn), currentTurn = nextPlayer)

    }
}

data class Board(
    val board: Array<Array<BoardCellValue>> = Array(9, {i -> Array(9, {j -> BoardCellValue.NONE})}),
    val activeBoard: Int = 0

) {
    fun makeMove(row:Int, col:Int, currentTurn: BoardCellValue) : Board {
        val newBoard : Array<Array<BoardCellValue>> = Array(9) { i ->
            Array(
                9
            ) { j -> board[i][j] }
        }
        newBoard[row][col] = currentTurn

        var newActiveBoard = 0
        if (advanceActiveBoard(newBoard, activeBoard)) {

             newActiveBoard = activeBoard + 1
        }
        return Board(newBoard, newActiveBoard)

    }

    fun isPlayable(i:Int, j:Int) : Boolean {
        val index :Int = i/3 + j/3
        return activeBoard == index
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



    //check for winner

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
    if (board[row][col] == board[row][col+1] && board[row][col+1] == board[row][col+1] && board[row][col+1] != BoardCellValue.NONE){
        return true
    }

    //horizontal second row
    if (board[row+1][col] == board[row+1][col+1] && board[row+1][col+1] == board[row+1 ][col+1] && board[row+ 1][col + 1] != BoardCellValue.NONE){
        return true
    }

    //horizontal third row
    if (board[row+2][col] == board[row+2][col+1] && board[row+2][col+1] == board[row+2 ][col+1] && board[row+ 2][col] != BoardCellValue.NONE){
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
    for (i in  row .. row+2){
        for (j in col..col+2) {
            if (board[row][col] == BoardCellValue.NONE) {
                return false
            }
        }
    }

    return false
}



enum class BoardCellValue{
    CIRCLE, CROSS, NONE
}

enum class VictoryType{

}