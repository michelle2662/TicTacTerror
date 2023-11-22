package kwan.tictacterror

import kotlin.math.pow
import kotlin.math.sqrt

enum class Difficulty {
    EASY, MEDIUM, HARD
}

class GameAi(private val difficulty: Difficulty = Difficulty.MEDIUM) {


    fun playNextMove(state: GameState): GameState {
        return when (difficulty) {
            Difficulty.EASY -> randomMove(state)
            Difficulty.MEDIUM,
            Difficulty.HARD -> closestToCenterMove(state)
        }
    }

    fun distance(p1: Point, p2: Point): Double {
        return sqrt((p1.x - p2.x).toDouble().pow(2.0) + (p1.y - p2.y).toDouble().pow(2.0))
    }

    private fun randomMove(state: GameState): GameState {
        val randomMove = state.possibleMoves().random()
        return state.playIJ(randomMove.x, randomMove.y)
    }

    private fun closestToCenterMove(state: GameState): GameState {
        val bestMove = state.possibleMoves().minByOrNull { distance(Point(5, 5), it) }!!
        return state.playIJ(bestMove.x, bestMove.y)
    }

}
