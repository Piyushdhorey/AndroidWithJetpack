package com.example.bullseyeprojectcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bullseyeprojectcompose.ui.theme.BullseyeProjectComposeTheme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BullseyeProjectComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Bullseye()
                }
            }
        }
    }
}

@Composable
fun Bullseye() {
    val randomNumber = remember {
        mutableStateOf((1..100).random())
    }
    val sliderPosition = remember {
        mutableStateOf(50F)
    }
    val score = remember {
        mutableStateOf(0)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Text(text = "🎯 BULLSEYE 🎯", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(bottom = 16.dp), fontFamily = FontFamily.Cursive)

        Text(text = "My Score is ${score.value} points")
        Spacer(modifier = Modifier.weight(1F))
        Text(text = "Match the slider as close as you can to the number given below!")
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Text(text = "${randomNumber.value}")

        Slider(value = sliderPosition.value, onValueChange = {
            sliderPosition.value = it
        },
            valueRange = 1F..100F,
            steps = 100,
        )
        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(text = "1")
            Spacer(modifier = Modifier.weight(1F))
            Text(text = "100")
        }

        Button(onClick = {
            val difference = abs(randomNumber.value - sliderPosition.value).toInt()

            if (difference == 0) {
                score.value += 10
            } else if (difference <= 5) {
                score.value += 5
            } else if (difference <= 10) {
                score.value += 1
            }

            randomNumber.value = (1..100).random()

            sliderPosition.value = 50F
        }, shape = MaterialTheme.shapes.large) {

            Text(text = "TEST IT")
        }
        Spacer(modifier = Modifier.weight(1F))
    }
}

@Preview(showBackground = true)
@Composable
fun BullseyePreview() {
    BullseyeProjectComposeTheme {
        Bullseye()
    }
}