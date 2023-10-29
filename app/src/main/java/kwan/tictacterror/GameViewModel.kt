package kwan.tictacterror

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val state = mutableStateOf(GameState())

    //when player click on certain square, update game board state
    fun playIJ( i: Int, j: Int){
        val nextPlayer : BoardCellValue
        val currentTurn = state.value.currentTurn
        if (currentTurn == BoardCellValue.CIRCLE){
            nextPlayer = BoardCellValue.CROSS
        }else {
            nextPlayer = BoardCellValue.CIRCLE
        }

        val newState = state.value.copy(board = state.value.board.makeMove(i,j,currentTurn), currentTurn = nextPlayer)

        state.value = newState
    }





}