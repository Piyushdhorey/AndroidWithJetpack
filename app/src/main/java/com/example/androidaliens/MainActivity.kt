package com.example.androidaliens

import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidaliens.ui.theme.AndroidAliensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidAliensTheme {
//                AndroidAliensRow()
//                AndroidAliensColumn()
                AndroidAliensGameOverBox()
            }
        }
    }
}

@Composable
fun AndroidAlien(
    color: Color,
    modifier: Modifier = Modifier
) {
    Image(modifier = modifier, painter = painterResource(id = R.drawable.android_alien), contentDescription = "", colorFilter = ColorFilter.tint(color))
}

@Composable
fun AndroidAliensRow() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AndroidAlien(color = Color.Red,
            modifier = Modifier
                .weight(1F)
                .padding(4.dp))

        AndroidAlien(color = Color.Yellow,
            modifier = Modifier
                .weight(1F)
                .padding(4.dp)
                )

        AndroidAlien(color = Color.Green,
            modifier = Modifier
                .weight(1F)
                .padding(4.dp))

        AndroidAlien(color = Color.Blue,
            modifier = Modifier
                .weight(1F)
                .padding(4.dp))

    }
}

@Composable
fun AndroidAliensGameOverBox() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        AndroidAliensRow()
        Spacer(modifier = Modifier
            .matchParentSize()
            .background(color = Color.Gray.copy(.6f)))
        Text(text = "GAME OVER",
            fontSize = 32.sp,
            fontStyle = FontStyle.Italic)
    }
}

@Composable
fun AndroidAliensColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidAlien(color = Color.Red,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp))

        AndroidAlien(color = Color.Green,
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp))
    }
}



@Preview(showBackground = true)
@Composable
fun AliensRowPreview() {
    AndroidAliensTheme {
        AndroidAliensRow()
    }
}

@Preview(showBackground = true)
@Composable
fun AliensColumnPreview() {
    AndroidAliensTheme {
        AndroidAliensColumn()
    }
}

@Preview(showBackground = true)
@Composable
fun AliensGamerOverPreview() {
    AndroidAliensTheme {
        AndroidAliensGameOverBox()
    }
}