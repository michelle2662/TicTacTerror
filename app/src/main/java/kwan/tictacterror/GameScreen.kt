package kwan.tictacterror

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kwan.tictacterror.ui.theme.Background


@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel { GameViewModel() },
    gameState:GameState = viewModel.state.value,
    modifier: Modifier = Modifier,
    navController: NavController,

) {
    Column(
        modifier = modifier
            .fillMaxSize() //for different phone screen size
            .background(Background) //set background color
            .padding(horizontal = 30.dp), //padding horizontally from both sides from edge

        horizontalAlignment = Alignment.CenterHorizontally, //keep child of column horizontal
        //verticalArrangement = Arrangement.spacedBy(10.dp) //keep space evenly

    ){

        //Player info
        PlayerInfo(gameState)

        //title
        Title()

        //draw game board
        GameBoard(gameState, viewModel)


        //player turn information
        PlayerTurn(viewModel)

        //undo
        Undo(gameState, viewModel)

        //

    }
}

@Composable
fun Undo(
    gameState: GameState,
    viewModel: GameViewModel
){
    IconButton(
        enabled = viewModel.canUndo(),
        onClick = { viewModel.undo() }
    ){
        Icon(Icons.Default.ArrowBack, contentDescription = "Undo")
    }
}


@Composable
fun Title(
    size : Int =  55
){
    //title
    Text(
        text = "Tic Tac Terror",
        fontSize = size.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Cursive,
        color = Color.Black,
        modifier = Modifier
            .padding(bottom = 60.dp)
    )
}
@Composable
fun PlayerInfo(gameState: GameState){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp, top = 80.dp),


        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "Player O: ${gameState.playerOScore}" , fontSize = 16.sp)
        Text(text = "Player X: ${gameState.playerXScore}" , fontSize = 16.sp)
    }
}
@Composable
fun GameBoard(
    gameState: GameState,
    viewModel:GameViewModel
) {
    //Game board
    Box(modifier = Modifier
        .wrapContentWidth()
        //.aspectRatio(1f)
        .shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(4.dp),
            clip = true

        )
        .border(BorderStroke(2.dp, Color.Black))
        .background(Background),
        contentAlignment = Alignment.Center
    ) {
        BoardBase()

        var showLine: Line? by remember { mutableStateOf(null) }
        LaunchedEffect(key1 = gameState.linesCreated) {
            gameState.linesCreated.forEach {
                showLine = it
                delay(300)
            }
            showLine = null
        }

        for (i in 0 until 9) {
            for (j in 0 until 9){
                val highLight = if (showLine?.containsPoint(j, i) == true) {
                    Modifier.background(Color.Red.copy(alpha = 0.5f))
                } else {
                    Modifier
                }
                var modifier = Modifier
                    .padding(start = ((300 / 9 * i) + 5).dp, top = (300 / 9 * j + 5).dp,)
                    .size(24.dp)
                    .align(Alignment.TopStart)
                    .then(highLight)
                if (gameState.board.isPlayable(j,i)){
                    modifier = modifier.clickable { viewModel.playIJ(j,i) }
                }
                Tile(gameState.board.board[j][i], modifier = modifier)
            }
        }
    }
}

@Composable
fun PlayerTurn(viewModel: GameViewModel){
    //Player turn
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = viewModel.getCurrentPlayer(),
            fontSize = 24.sp,
            fontStyle = FontStyle.Italic)

        Button(
            onClick = {viewModel.restart()},
            shape = RoundedCornerShape(5.dp),
            elevation = ButtonDefaults.buttonElevation(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.Black)
        ) {
            Text(text = "Restart", fontSize = 16.sp, fontStyle = FontStyle.Italic)
        }

    }
}

@Composable
fun Tile(
    state:BoardCellValue,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        if (state == BoardCellValue.CIRCLE) {
            Circle()
        }else if (state == BoardCellValue.CROSS) {
            Cross()
        }
    }
}


