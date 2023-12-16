package kwan.tictacterror

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun SelectPlayerScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize() //for different phone screen size
            .padding(horizontal = 10.dp), //set background color


        horizontalAlignment = Alignment.CenterHorizontally, //keep child of column horizontal
        //verticalArrangement = Arrangement.spacedBy(10.dp) //keep space evenly
    ){

        Spacer(Modifier.height(150.dp))
        Title(70)

        SelectPlayer(viewModel, navController)

    }
}

@Composable
fun SelectPlayer(
    viewModel: GameViewModel,
    navController: NavController
) {
    Column(
        Modifier.padding(vertical = 110.dp),
        horizontalAlignment = Alignment.CenterHorizontally, //keep child of column horizontal


    ){
        var displayLevel by remember { mutableStateOf(false) }
        Button(
//            modifier = Modifier.clickable{
//                navController.navigate(route = Screen.gameScreen.route)
//            },
            onClick = {
                if (displayLevel){
                    displayLevel = false
                }else {
                    displayLevel = true
                }
                //viewModel.setMode(Mode.SinglePlayer(GameAi()))
                //navController.navigate(route = Screen.GameScreen.route)
            },
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            //colors = ButtonDefaults.buttonColors()

        ){
            Text(text ="Single Player",fontSize = 24.sp, fontStyle = FontStyle.Italic)
        }

        val font = 16.sp

            AnimatedVisibility(visible = displayLevel) {
                val buttonModifier = Modifier
                    .width(110.dp)
                    .height(50.dp) // Adjust the height as needed

                Column(
                    Modifier.padding(13.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Button(
                        onClick = {
                            viewModel.setMode(Mode.SinglePlayer(GameAi(difficulty = Difficulty.EASY)))
                            navController.navigate(route = Screen.GameScreen.route)
                        },
                        modifier = buttonModifier,
                        shape = RoundedCornerShape(5.dp),


                        ) {
                        Text("Easy", fontSize = font )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.setMode(Mode.SinglePlayer(GameAi(difficulty = Difficulty.MEDIUM)))
                            navController.navigate(route = Screen.GameScreen.route)
                        },
                        modifier = buttonModifier,
                        shape = RoundedCornerShape(5.dp),


                        ) {
                        Text("Medium",fontSize = font)
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.setMode(Mode.SinglePlayer(GameAi(difficulty = Difficulty.HARD)))
                            navController.navigate(route = Screen.GameScreen.route)
                        },
                        modifier = buttonModifier,
                        shape = RoundedCornerShape(5.dp),


                        ) {
                        Text("Hard", fontSize = font)
                    }
                }


            }



        Spacer(modifier = Modifier.height(25.dp))

        Button(
//            modifier = Modifier.clickable{
//                navController.navigate(route = Screen.gameScreen.route)
//            },
            onClick = {
                viewModel.setMode(Mode.Multiplayer)
                navController.navigate(route = Screen.GameScreen.route)
            },
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Gray,
//                contentColor = Color.Black)
        ){
            Text(text ="Two Player",fontSize = 24.sp, fontStyle = FontStyle.Italic)
        }
    }

}

