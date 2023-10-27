package kwan.tictacterror

data class GameState(
    val playerOScore: Int = 0,
    val playerXScore: Int = 0,
    //val drawCount: Int = 0,
    //val hintText: String = "Player 'O' turn",
    val currentTurn:BoardCellValue = BoardCellValue.CIRCLE,
    val hasWon: Boolean = false,
    val victoryCount: Int = 0,
    val board:Board = Board(),
    //val victoryType: VictoryType
)

data class Board(
    val board: Array<Array<BoardCellValue>> = Array(9, {i -> Array(9, {j -> BoardCellValue.NONE})})
) {
    fun makeMove(row:Int, col:Int, currentTurn: BoardCellValue) : Board {
        val newBoard : Array<Array<BoardCellValue>> = Array(9, {i -> Array(9, {j -> board[i][j]})})
        newBoard[row][col] = currentTurn
        return Board(newBoard)

    }
}
enum class BoardCellValue{
    CIRCLE, CROSS, NONE
}

enum class VictoryType{

}