package kwan.tictacterror

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val state = mutableStateOf(GameState())

    //when player click on certain square, update game board state
    fun playIJ( i: Int, j: Int){
        state.value = state.value.playIJ(i,j)
    }

    fun restart(){
        state.value = state.value.reset()
    }

    fun getCurrentPlayer(): String{
        var turn = state.value.currentTurn
        if (turn == BoardCellValue.CIRCLE){
            return "Player O's turn"
        }else {
            return "Player X's turn"
        }
    }




}