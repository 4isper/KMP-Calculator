package com.example.kmpproject

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import evaluate

@Composable
fun App(modifier: Any) {
    MaterialTheme {
        val buttons = listOf("AC", "(", ")", "/",
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            "0", ",", "del", "=")

        var string by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ResizableText(text = string,
                modifier = Modifier
                    .fillMaxWidth(),
                maxFontSize = 75,
                minFontSize = 15
            )
            ResizableText(text = result,
                modifier = Modifier
                    .fillMaxWidth(),
                maxFontSize = 50,
                minFontSize = 30
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxSize().padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items(buttons){ button ->
                Button(
                    modifier = Modifier
                        .size(90.dp)
                        .padding(2.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF2A4070)),
                    shape = RoundedCornerShape(27.dp),
                    contentPadding = ButtonDefaults.TextButtonContentPadding,
                    onClick = {
                        when (button) {
                            "AC" -> {
                                string = ""
                                result = ""
                            }

                            "del" -> {
                                if (string.isNotEmpty()) {
                                    string = string.dropLast(1)
                                }
                            }

                            "=" -> {
                                result = evaluate(string).toString()
                            }

                            else -> {
                                string += button
                            }
                        }
                    }
                ) {
                    Text(text = button,
                        fontSize = 40.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ResizableText(text: String, modifier: Modifier, maxFontSize: Int, minFontSize: Int) {
    val calculatedFontSize = (maxFontSize - text.length * 2).coerceAtLeast(minFontSize).sp

    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth(),
        fontSize = calculatedFontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}