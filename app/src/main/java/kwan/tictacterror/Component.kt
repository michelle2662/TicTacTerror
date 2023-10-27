package kwan.tictacterror

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//board
@Composable
fun BoardBase() {
    Canvas( modifier = Modifier
        .size(300.dp)
    ) {



        //vertical line left
        drawLine(
            color = Color.Black,
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width*1/3 , y = 0f),
            end = Offset(x = size.width*1/3, y = size.height)
        )

        //vertical line right
        drawLine(
            color = Color.Black,
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            start = Offset(x = size.width*2/3 , y = 0f),
            end = Offset(x = size.width*2/3, y = size.height)
        )

        //Horizontal line left
        drawLine(
            color = Color.Black,
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height*1/3 ),
            end = Offset( x = size.width , y = size.height*1/3)
        )

        //Horizontal line left
        drawLine(
            color = Color.Black,
            strokeWidth = 15f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height*2/3 ),
            end = Offset( x = size.width , y = size.height*2/3)
        )

        //board for individual game
        for (i in 1..8) {
            if ( i != 3 && i != 6){
                drawLine(
                    color = Color.Black,
                    strokeWidth = 5f,
                    cap = StrokeCap.Round,
                    start = Offset(x = 0f, y = size.height/9*i),
                    end = Offset(x = size.width, y = size.height/9*i)
                )

                drawLine(
                    color = Color.Black,
                    strokeWidth = 5f,
                    cap = StrokeCap.Round,
                    start = Offset(x = size.width/9*i, y = 0f),
                    end = Offset(x = size.width/9*i, y = size.height)
                )
            }

        }


    }
}

//Cross
@Composable
fun Cross(){
    Canvas(
        modifier = Modifier
            .size(7.dp)
    ) {
        drawLine(
            color = Color.Green,
            strokeWidth = 5f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
        
        drawLine(
            color = Color.Green,
            strokeWidth = 5f,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}

//Circle
@Composable
fun Circle(){
    Canvas(
        modifier = Modifier
            .size(10.dp)
    ) {
        drawCircle( color = Color.Blue, style = Stroke(width = 5f))
    }
}

//Horizontal Line
@Preview
@Composable
fun Previews() {
    //Cross()
    Circle()
    //BoardBase()
}