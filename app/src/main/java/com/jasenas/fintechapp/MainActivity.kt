package com.jasenas.fintechapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jasenas.fintechapp.ui.theme.FinTechAppTheme

class MainActivity : ComponentActivity() {
    private val processor = TextProcessor()
    private var resultText by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinTechAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainView(
                        modifier = Modifier.padding(innerPadding),
                        resultText = resultText,
                        onProcess = { text, option ->
                            handleProcess(text, option)
                        }
                    )
                }
            }
        }
    }

    private fun handleProcess(text: String, option: String) {
        val validation = processor.validateTextInput(text)
        if (!validation.isValid) {
            val errorMsg = validation.errorMessageRes?.let { getString(it) } ?: "Invalid input"
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
        } else {
            val wordsOption = getString(R.string.option_words)
            val separatorsOption = getString(R.string.option_separators)
            val count = when (option) {
                wordsOption -> processor.countWords(text)
                separatorsOption -> processor.countSeparators(text)
                else -> 0
            }
            resultText = getString(R.string.result_prefix, count)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    resultText: String = "",
    onProcess: (String, String) -> Unit = { _, _ -> }
) {
    var inputText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    
    val wordsOption = stringResource(R.string.option_words)
    val separatorsOption = stringResource(R.string.option_separators)
    val options = listOf(wordsOption, separatorsOption)
    var selectedOption by remember(wordsOption) { mutableStateOf(wordsOption) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text(stringResource(R.string.label_input_text)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.label_mode)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(onClick = { onProcess(inputText, selectedOption) }) {
            Text(stringResource(R.string.button_process))
        }

        if (resultText.isNotEmpty()) {
            Text(
                text = resultText,
                style = MaterialTheme.typography.headlineSmall
            )
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
