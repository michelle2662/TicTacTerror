package kwan.tictacterror

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class UiState(
    val game: GameState,
    val mode: Mode,
    val animatedLine: Line?,
    val animating: Boolean,
    val canUndo: Boolean
) {
    companion object {
        fun newGame() = UiState(
            game = GameState(),
            mode = Mode.Unknown,
            animatedLine = null,
            animating = false,
            canUndo = false
        )
    }

    val requireUserInput: Boolean get() {
        return !game.hasWon
                && !animating
                && currentPlayerIsHuman
    }

    val currentPlayerIsHuman: Boolean get() {
        return mode.playerIsHuman(game.currentTurn)
    }
}


sealed interface GameEvent {
    object None: GameEvent
    data class Start(val mode: Mode): GameEvent
    data class PlayMove(val i: Int, val j: Int): GameEvent
    object Undo: GameEvent
    object Restart: GameEvent
}

sealed interface Player {
    data object Human: Player
    class Cpu(val ai: GameAi): Player
}


class GameViewModel: ViewModel() {
    //val state = mutableStateOf(GameState())
    val uiState = mutableStateOf(UiState.newGame())

    val _events: MutableStateFlow<GameEvent> = MutableStateFlow(GameEvent.None)
    val events: Flow<GameEvent> get() = _events

    private fun updateState(body: UiState.() -> UiState) {
        uiState.value = uiState.value.body()
    }

    private fun playMove(move: GameEvent.PlayMove) {
        updateState {
            copy(
                game = game.playIJ(move.i, move.j),
            )
        }
    }

    init {
        viewModelScope.launch {
            events.collect { event ->
                handleEvent(uiState.value.game, event)
            }

        }
    }

    private suspend fun handleEvent(currentGame: GameState, event: GameEvent) {
        when(event) {
            is GameEvent.Start -> {
                updateState {
                    UiState(
                        game = GameState(),
                        animatedLine = null,
                        mode = event.mode,
                        animating = false,
                        canUndo = false
                    )
                }
            }
            is GameEvent.PlayMove -> {
                val newGame = currentGame.playIJ(event.i, event.j)
                playAndRunAnimations(newGame)
                if (!newGame.hasWon) {
                    uiState.value.mode.let { mode ->
                        if (mode is Mode.SinglePlayer) {
                            val aiMove = mode.ai.playNextMove(newGame)
                            playAndRunAnimations(aiMove)
                        }
                    }
                }
            }
            GameEvent.Undo -> {
                check(uiState.value.currentPlayerIsHuman)
                updateState {
                    copy(
                        game = game.undo(),
                        animating = false,
                        animatedLine = null,
                        canUndo = false
                    )
                }
                if (!uiState.value.currentPlayerIsHuman) {
                    updateState {
                        copy(
                            game = game.undo(),
                            animating = false,
                            animatedLine = null,
                            canUndo = false
                        )
                    }
                }
            }
            GameEvent.Restart -> {
                updateState {
                    copy(
                        game = GameState(),
                        animatedLine = null,
                        animating = false,
                        canUndo = false
                    )
                }
            }
            GameEvent.None -> {}
        }
    }

    private suspend fun playAndRunAnimations(newGame: GameState) {
        val needsAnimation = newGame.linesCreated.isNotEmpty()
        updateState {
            copy(
                game = newGame,
                animatedLine = null,
                animating = needsAnimation,
                canUndo = !needsAnimation
            )
        }
        if (needsAnimation) {
            newGame.linesCreated.forEach {
                delay(250)
                updateState {
                    copy(animatedLine = it, animating = true)
                }
            }
            delay(100)
            updateState {
                copy(
                    animatedLine = null,
                    animating = false,
                    canUndo = true
                )
            }
            delay(100)
        }
    }

    //private var gameMode: Mode = Mode.SinglePlayer(GameAi())
    //private var canUndo = false

    //when player click on certain square, update game board state
    fun playIJ(i: Int, j: Int) {
        viewModelScope.launch {
            _events.emit(GameEvent.PlayMove(i, j))
        }
        //state.value = state.value.playIJ(i,j)
        //if (gameMode is Mode.SinglePlayer && !state.value.hasWon) {
        //    state.value = (gameMode as Mode.SinglePlayer).ai.playNextMove(state.value)
        //}
        //canUndo = true
    }

    fun undo() {
        viewModelScope.launch {
            _events.emit(GameEvent.Undo)
        }

        //state.value = state.value.undo()
        //if (gameMode is Mode.SinglePlayer) {
        //    state.value = state.value.undo()
        //}
        //canUndo = false
    }

    fun canUndo():Boolean{
        return uiState.value.canUndo
        //return canUndo && state.value.previousState != null
    }

    fun showDirections(): Boolean {
        return !uiState.value.game.gameStarted
        //return !state.value.gameStarted
    }

    fun restart() {
        viewModelScope.launch {
            _events.emit(GameEvent.Restart)
        }
        //state.value = GameState()
        //canUndo = false
    }

    fun getCurrentPlayer(): String {
        //val turn = state.value.currentTurn
        val turn = uiState.value.game.currentTurn
        return if (turn == BoardCellValue.CIRCLE){
            "Player O's turn"
        }else {
            "Player X's turn"
        }
    }

    fun setMode(mode: Mode) {
        viewModelScope.launch {
            _events.emit(GameEvent.Start(mode))
        }
        //this.gameMode = mode
        //when (mode) {
        //    is Mode.SinglePlayer -> {
        //        state.value = GameState()
        //        canUndo = false
        //    }
        //    is Mode.Multiplayer -> {
        //        state.value = GameState()
        //        canUndo = false
        //    }
        //    is Mode.Unknown -> { error("Cannot be set to unknown") }
        //}
        //restart()
    }
}

private fun Mode.playerIsHuman(boardCellValue: BoardCellValue): Boolean {
    when (this) {
        is Mode.SinglePlayer -> {
            return boardCellValue != cpuPlayer
        }
        is Mode.Multiplayer -> {
            return true
        }
        else -> {
            return false
        }
    }
}

sealed interface Mode {

    data class SinglePlayer(
        val ai: GameAi,
        val cpuPlayer: BoardCellValue = BoardCellValue.CIRCLE
    ): Mode

    data object Multiplayer: Mode

    data object Unknown: Mode
}
