package com.jasenas.fintechapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jasenas.fintechapp.ui.theme.FinTechAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinTechAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier = Modifier) {
    var greetingText by remember { mutableStateOf("") }
    var textColor by remember { mutableStateOf(Color.Black) }
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = greetingText, color = textColor)
        Button(onClick = { greetingText = "hello" }) {
            Text("Say hello")
        }
        Button(onClick = {
            textColor = if (textColor == primaryColor)
                Color.Black
            else primaryColor
        }) {
            Text("Change text color")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview() {
    FinTechAppTheme {
        MainView()
    }
}
