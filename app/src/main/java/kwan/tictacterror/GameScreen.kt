package kwan.tictacterror

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kwan.tictacterror.ui.theme.Background

@Composable
fun GameScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize() //for different phone screen size
            .background(Background) //set background color
            .padding(horizontal = 30.dp), //padding horizontally from both sides from edge

        horizontalAlignment = Alignment.CenterHorizontally, //keep child of column horizontal
        verticalArrangement = Arrangement.SpaceEvenly //keep space evenly

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Player O: 0" , fontSize = 16.sp)
            Text(text = "Player X: 0" , fontSize = 16.sp)
        }

        Text(
            text = "Tic Tac Terror",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Color.Black

        )
    }
}

@Preview
@Composable
fun Prev(){
    GameScreen()
}