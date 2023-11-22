package kwan.tictacterror

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val state = mutableStateOf(GameState())
    var gameMode = Mode.SINGLEPLAYER

    //when player click on certain square, update game board state
    fun playIJ( i: Int, j: Int){
        state.value = state.value.playIJ(i,j)
    }

    fun undo() {
        state.value = state.value.undo()
    }

    fun canUndo():Boolean{
        return state.value.previousBoard != state.value.board
    }

    fun BoardDirectionNoShow():Boolean{
        return state.value.gameStarted
    }

    fun restart(){
        state.value = GameState()
    }

    fun getCurrentPlayer(): String{
        var turn = state.value.currentTurn
        if (turn == BoardCellValue.CIRCLE){
            return "Player O's turn"
        }else {
            return "Player X's turn"
        }
    }

    fun setMode(mode: Mode) {
        this.gameMode = mode
    }



}

enum class Mode{
    SINGLEPLAYER, MULTIPLAYER
}
