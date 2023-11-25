package kwan.tictacterror

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val state = mutableStateOf(GameState())

    private var gameMode: Mode = Mode.SinglePlayer(GameAi())
    private var canUndo = false

    //when player click on certain square, update game board state
    fun playIJ(i: Int, j: Int){
        state.value = state.value.playIJ(i,j)
        if (gameMode is Mode.SinglePlayer && !state.value.hasWon) {
            state.value = (gameMode as Mode.SinglePlayer).ai.playNextMove(state.value)
        }
        canUndo = true
    }

    fun undo() {
        state.value = state.value.undo()
        if (gameMode is Mode.SinglePlayer) {
            state.value = state.value.undo()
        }
        canUndo = false
    }

    fun canUndo():Boolean{
        return canUndo && state.value.previousState != null
    }

    fun showDirections(): Boolean {
        return !state.value.gameStarted
    }

    fun restart() {
        state.value = GameState()
        canUndo = false
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
        restart()
    }
}

sealed interface Mode {

    data class SinglePlayer(val ai: GameAi): Mode

    data object Multiplayer: Mode
}
