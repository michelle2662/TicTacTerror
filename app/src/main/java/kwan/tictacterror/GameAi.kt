package kwan.tictacterror

import android.util.Log
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

enum class Difficulty {
    EASY, MEDIUM, HARD
}

private const val MAX_DEPTH = 4

class GameAi(private val difficulty: Difficulty = Difficulty.MEDIUM) {

    fun playNextMove(state: GameState): GameState {
        return when (difficulty) {
            Difficulty.EASY -> randomMove(state)
            Difficulty.MEDIUM,
            Difficulty.HARD -> minimax(state)
        }
    }

    private fun randomMove(state: GameState): GameState {
        val randomMove = state.possibleMoves().toList().random()
        return state.playIJ(randomMove.x, randomMove.y)
    }

    private fun minimax(
        state: GameState,
        depth: Int = MAX_DEPTH,
        currentPlayer: BoardCellValue = state.currentTurn,
        maximizingPlayer: Boolean = true
    ): GameState {
        if (depth == 0 || state.hasWon) {
            return state
        }
        if (maximizingPlayer) {
            var maxEval = Double.MIN_VALUE
            var bestMove: GameState? = null
            for (move in state.possibleMoves()) {
                val eval = minimax(state.playIJ(move.x, move.y), depth - 1,  currentPlayer, false).score(currentPlayer, move)
                if (eval > maxEval || bestMove == null) {
                    maxEval = eval
                    bestMove = state.playIJ(move.x, move.y)
                    Log.d("MOVE", "maximixing ($currentPlayer): $eval: $move")
                }
            }
            return bestMove ?: state
        } else {
            var minEval = Double.MAX_VALUE
            var bestMove: GameState? = null
            for (move in state.possibleMoves()) {
                val eval = minimax(state.playIJ(move.x, move.y), depth - 1, currentPlayer , true).score(currentPlayer, move)
                if (eval < minEval || bestMove == null) {
                    minEval = eval
                    bestMove = state.playIJ(move.x, move.y)
                    Log.d("MOVE", "minimizing ($currentPlayer): $eval: $move")
                }
            }
            return bestMove ?: state
        }
    }
}

private fun GameState.score(
    currentPlayer: BoardCellValue,
    move: Point
): Double {
    return if (currentPlayer == BoardCellValue.CIRCLE) {
        when (winner) {
            BoardCellValue.CIRCLE -> Double.MAX_VALUE
            BoardCellValue.CROSS -> Double.MIN_VALUE
            BoardCellValue.NONE -> 0
            else -> (playerOScore - playerXScore) + heuristic(move)
        }.toDouble()
    } else {
        when (winner) {
            BoardCellValue.CIRCLE -> Double.MIN_VALUE
            BoardCellValue.CROSS -> Double.MAX_VALUE
            BoardCellValue.NONE -> 0
            else -> (playerXScore - playerOScore) + heuristic(move)
        }.toDouble()
    }
}

private const val CENTER = 4

private fun heuristic(move: Point) = staticHeuristic(move)

private fun staticHeuristic(move: Point): Double {
    return heuristics[move.x][move.y] / 10.0
}

private fun distanceHeuristic(move: Point): Double {
    val dx = (CENTER - move.x).absoluteValue
    val dy = (CENTER - move.y).absoluteValue
    val toCenter = CENTER - maxOf(dx, dy)
    return 0.99 * (toCenter / CENTER.toDouble())
}


// 1 2 3 | 1 2 3 | 1 1 1
// 2 4 4 | 3 3 4 | 4 3 2
// 3 4 5 | 5 4 5 | 5 4 3
/////////////////////////
// 1 2 3 | 3 3 3 | 3 2 1
// 1 3 4 | 3 3 3 | 4 4 2
// 1 2 3 | 3 3 3 | 5 4 3
/////////////////////////
// 3 4 5 | 5 4 5 | 5 4 1
// 2 3 4 | 4 3 3 | 4 3 1
// 1 1 1 | 3 2 1 | 3 2 1

val heuristics = listOf(
    listOf(1, 2, 3, 1, 2, 3, 1, 1, 1),
    listOf(2, 4, 4, 3, 3, 4, 3, 3, 2),
    listOf(3, 4, 5, 5, 4, 5, 5, 4, 3),
    listOf(1, 3, 4, 3, 3, 3, 4, 4, 2),
    listOf(1, 2, 3, 3, 3, 3, 5, 4, 3),
    listOf(1, 2, 3, 3, 3, 3, 5, 4, 3),
    listOf(3, 4, 5, 5, 4, 5, 5, 3, 1),
    listOf(2, 3, 3, 4, 3, 3, 4, 3, 1),
    listOf(1, 1, 1, 3, 2, 1, 3, 2, 1)
)
