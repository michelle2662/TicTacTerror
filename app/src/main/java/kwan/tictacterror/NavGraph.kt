package kwan.tictacterror

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.SelectPlayerScreen.route
    ){
        composable(
            route = Screen.SelectPlayerScreen.route
        ){
            SelectPlayerScreen(navController = navController)

        }

        composable(
            route = Screen.GameScreen.route
        ){
            GameScreen(navController = navController)

        }
    }
}