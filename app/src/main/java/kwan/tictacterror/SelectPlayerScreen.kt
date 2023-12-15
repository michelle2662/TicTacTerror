package kwan.tictacterror

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kwan.tictacterror.ui.theme.Background


@Composable
fun SelectPlayerScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize() //for different phone screen size
            .background(Background)
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
        Button(
//            modifier = Modifier.clickable{
//                navController.navigate(route = Screen.gameScreen.route)
//            },
            onClick = {
                viewModel.setMode(Mode.SinglePlayer(GameAi()))
                navController.navigate(route = Screen.GameScreen.route)
            },
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.Black)

        ){
            Text(text ="One Player",fontSize = 24.sp, fontStyle = FontStyle.Italic)
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.Black)
        ){
            Text(text ="Two Player",fontSize = 24.sp, fontStyle = FontStyle.Italic)
        }
    }

}

