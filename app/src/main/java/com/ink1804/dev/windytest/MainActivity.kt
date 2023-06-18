package com.ink1804.dev.windytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.ink1804.dev.windytest.ui.theme.WindyTestTheme

class MainActivity : ComponentActivity() {

    val viewModel: MainActivityViewModel = MainActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindyTestTheme {
                MainActivityContent(viewModel)
            }
        }
    }
}

@Composable
fun MainActivityContent(viewModel: MainActivityViewModel) {
    var input by remember { mutableStateOf("") }
    val resultFlow by viewModel.summFlow.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = input,
            label = { Text(text = "Input Number") },
            onValueChange = { newValue ->
                val filteredValue = newValue.replace(Regex("\\D"), "")
                input = filteredValue
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            )

        )
        Button(onClick = {
            viewModel.startSummation(input.toIntOrNull() ?: 0)
        }) {
            Text(text = "Start")

        }
        Text(text = "Result: $resultFlow")
    }
}


@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    WindyTestTheme {
        MainActivityContent(MainActivityViewModel())
    }
}

