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
}