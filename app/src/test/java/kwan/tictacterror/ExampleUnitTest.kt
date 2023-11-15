package kwan.tictacterror

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun changingTurn_isCorrect(){
        var gameState = GameState()
        assertEquals(gameState.currentTurn, BoardCellValue.CROSS)
        gameState = gameState.playIJ(0,0)
        assertEquals(gameState.currentTurn, BoardCellValue.CIRCLE)
        gameState = gameState.playIJ(1,1)
        assertEquals(gameState.currentTurn, BoardCellValue.CROSS)

    }

    @Test
    fun horizontalRow_isCorrect(){
        var gameState = GameState()
        val test_board: Array<Array<BoardCellValue>> = Array(9, {i -> Array(9, {j -> BoardCellValue.NONE})})
        test_board[0][0] = BoardCellValue.CIRCLE
        test_board[0][1] = BoardCellValue.CIRCLE
        test_board[0][2] = BoardCellValue.CIRCLE
        var board = Board(test_board,0)
        assertEquals(board.activeBoard, 0)
        board = board.makeMove(1,1, BoardCellValue.CIRCLE)
        assertEquals(board.activeBoard, 1)

        board = Board(test_board, 0)
        //grid 1 second row
        test_board[1][0] = BoardCellValue.CIRCLE
        test_board[1][1] = BoardCellValue.CIRCLE
        test_board[1][2] = BoardCellValue.CIRCLE
        board = Board(test_board,0)
        assertEquals(board.activeBoard, 0)
        board = board.makeMove(2,1, BoardCellValue.CIRCLE)
        assertEquals(board.activeBoard, 1)

        board = Board(test_board, 0)
        //grid 1 third row
        test_board[2][0] = BoardCellValue.CIRCLE
        test_board[2][1] = BoardCellValue.CIRCLE
        test_board[2][2] = BoardCellValue.CIRCLE
        board = Board(test_board,0)
        assertEquals(board.activeBoard, 0)
        board = board.makeMove(2,1, BoardCellValue.CIRCLE)
        assertEquals(board.activeBoard, 1)

        //grid 1 third row
        test_board[0][2] = BoardCellValue.CIRCLE
        test_board[0][3] = BoardCellValue.CIRCLE
        test_board[0][4] = BoardCellValue.CIRCLE
        board = Board(test_board,1)
        assertEquals(board.activeBoard, 1)
        board = board.makeMove(2,1, BoardCellValue.CIRCLE)
        assertEquals(board.activeBoard, 2)

    }
}