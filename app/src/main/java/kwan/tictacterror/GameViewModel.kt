package kwan.tictacterror

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    val state = mutableStateOf(GameState())

    //when player click on certain square, update game board state
    fun playIJ( i: Int, j: Int){
        state.value = state.value.playIJ(i,j)
    }





}