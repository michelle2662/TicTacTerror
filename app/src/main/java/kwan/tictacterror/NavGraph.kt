package kwan.tictacterror

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: GameViewModel = viewModel { GameViewModel() }
){
    NavHost(
        navController = navController,
        startDestination = Screen.SelectPlayerScreen.route
    ){
        composable(
            route = Screen.SelectPlayerScreen.route
        ){
            SelectPlayerScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.GameScreen.route
        ){
            GameScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
