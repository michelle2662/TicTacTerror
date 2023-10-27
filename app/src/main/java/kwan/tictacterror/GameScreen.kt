package kwan.tictacterror

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
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
        //verticalArrangement = Arrangement.spacedBy(10.dp) //keep space evenly

    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp, top = 80.dp),


            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Player O: 0" , fontSize = 16.sp)
            Text(text = "Draw: O", fontSize = 16.sp)
            Text(text = "Player X: 0" , fontSize = 16.sp)
        }

        //title
        Text(
            text = "Tic Tac Terror",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 60.dp)
        )

        Box(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)

                )
                .background(Background),
                contentAlignment = Alignment.Center
        ) {
            BoardBase()
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Player O turn",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic)

            Button(
                onClick = { /*TODO*/ },
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
}

@Preview
@Composable
fun Prev(){
    GameScreen()
}