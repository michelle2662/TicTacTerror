package kwan.tictacterror

sealed class Screen (val route:String){
    object SelectPlayerScreen:Screen(route = "select_player")
    object GameScreen:Screen(route = "game_screen")
}