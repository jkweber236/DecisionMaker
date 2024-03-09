package com.example.decisionmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.decisionmaker.ui.theme.DarkBlue
import com.example.decisionmaker.ui.theme.DecisionMakerTheme
import com.example.decisionmaker.ui.theme.LightBlue
import kotlin.random.Random
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecisionMakerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = LightBlue) {
                    var option by remember { mutableStateOf("") }
                    var optionsList by remember { mutableStateOf(emptyList<String>()) }
                    var decision by remember { mutableStateOf<String?>(null) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Row for the input box and the "+" button
                        Row(
//                            modifier = Modifier.fillMaxWidth(),

                        ) {
                            // TextField for user input with multiline support
                            TextField(
                                value = option,
                                onValueChange = {
                                    option = it
                                },
                                label = { Text("Enter your decision options") },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        if (option.isNotBlank()) {
                                            optionsList = optionsList + option.trim()
                                            option = ""
                                        }
                                    }
                                ),
                                modifier = Modifier
                                    .weight(1f) // Take up remaining horizontal space
                            )

                            // Button to add options.
                            Button(
                                onClick = {
                                    if (option.isNotBlank()) {
                                        optionsList = optionsList + option.trim()
                                        option = ""
                                    }
                                },
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .clip(RectangleShape)
                                    .width(50.dp)
                                    .height(50.dp)
                            ) {
                                Text("+")
                            }
                        }

                        // Button to make a decision
                        Button(
                            onClick = {
                                if (optionsList.isNotEmpty()) {
                                    decision = makeDecision(optionsList)
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Make decision")
                        }

                        // Decision box
                        decision?.let { decision ->
                            // Pass the decision value to the DisplayDecision composable
                            DisplayDecision("Decision", DarkBlue, decision)
                        }

                        // List of options
                        optionsList.forEach { option ->
                            Text(
                                text = option,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayDecision(name: String, bgColor: Color, decision: String, modifier: Modifier = Modifier) {
    Surface(color = bgColor, modifier = modifier) {
        Text(
            text = "$name: $decision",
            // Add padding for better visibility
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayDecisionPreview() {
    DecisionMakerTheme {
        val decision = makeDecision(listOf("Option1", "Option2", "Option3")) // Default value for preview
        DisplayDecision("Decision", DarkBlue, decision)
    }
}

// Function to select a random option.
fun makeDecision(options: List<String>): String {

    val random = Random
    if (options.isNotEmpty()) {
        // Selects a index within range of the list.
        val selectedIndex = random.nextInt(options.size)
        // Return the option at the specified index.
        return options[selectedIndex]
    }
    return "No options available"
}
