package com.example.decisionmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.decisionmaker.ui.theme.OrangeRed
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.example.decisionmaker.ui.theme.LighterBlue
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecisionMakerTheme {
                // A surface container to set the background
                Surface(modifier = Modifier.fillMaxSize(), color = LightBlue) {

                    // Initialize variable to store an option from the user
                    var option by remember { mutableStateOf("") }
                    // Initialize variable to hold all options from the user
                    var optionsList by remember { mutableStateOf(emptyList<String>()) }
                    // Intialize variable to store the decision made by the program
                    var decision by remember { mutableStateOf<String?>(null) }

                    // Column layout to organize elements vertically
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // Header section with the app title
                        // Setting the appearance of the rectangle holding the app name
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 25.dp)
                                .background(OrangeRed)
                                .height(70.dp)
                        ) {
                            // Setting the appearance of the app name
                            Text(
                                text = "Decision Maker",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .padding(start = 8.dp),
                                fontSize = 35.sp
                            )
                        }

                        // Row for the input box and the "+" button
                        Row()
                        {
                            // Section for user to enter options
                            TextField(
                                // Storing the current (or most recent) option input
                                value = option,
                                // Updates the current option when the input value changes
                                onValueChange = {
                                    option = it
                                },
                                // Instructions displayed in the input field
                                label = { Text("Enter your options") },

                                // Styling and layout properties for the text
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .weight(1f) // Take up remaining horizontal space
                            )

                            // Button to add the option typed into the input box
                            Button(
                                // Execute whenever the add ("+") button is clicked
                                onClick = {
                                    // Check if the input is not blank, then add the trimmed
//                                  // option the the list
                                    if (option.isNotBlank()) {
                                        optionsList = optionsList + option.trim()
                                        // Reset the option variable to an empty string
                                        option = ""
                                    }
                                },
                                // Setting the appearance of the "+" button
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .clip(RectangleShape)
                                    .background(color = DarkBlue, shape = RectangleShape)
                                    .width(56.dp)
                                    .height(56.dp)

                            ) {
                                // Adding text to the "+" button
                                Text("+")
                            }
                        }

                        // Button to have the program make and display a decision
                        Button(
                            onClick = {
                                // Makes a decision if the list of options is not empty
                                if (optionsList.isNotEmpty()) {
                                    decision = makeDecision(optionsList)
                                }
                            },
                            // Sets the appearance of the decision button
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .padding(bottom = 30.dp)
                                .padding(start = 80.dp, end = 80.dp)
                                .width(250.dp)

                        ) {
                            // Adding text to the decision button
                            Text("Make decision",
                                fontSize = 28.sp)
                        }

                        // Display decision box
                        decision?.let { decision ->
                            // Pass the decision value to the DisplayDecision function
                            DisplayDecision(LighterBlue, decision,
                                // Set the spacing of the decision display box
                                modifier = Modifier
                                    .padding(start = 30.dp, end = 30.dp)
                                    .padding(bottom = 30.dp))
                        }

                        // Displays each of the options to the user
                        optionsList.forEach { option ->
                            Text(
                                // Set the appearance of the options
                                text = "> $option",
                                fontSize = 28.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(bottom = 5.dp)
                                    .padding(start = 30.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Function to display the decision with a colored background
@Composable
fun DisplayDecision(bgColor: Color, decision: String, modifier: Modifier = Modifier) {
    Surface(color = bgColor, modifier = modifier) {
        // Split the decision by word to display each on a new line
        val words = decision.split(" ")
        Column(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .wrapContentSize(Alignment.Center)
        ) {
            // Set the appearance of and display each individual word in the decision
            words.forEach { word ->
                Text(
                    text = word,
                    textAlign = TextAlign.Center,
                    fontSize = calculateFontSize(word).sp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Black
                )
            }
        }
    }
}

// Function to select a random option from the given list
fun makeDecision(options: List<String>): String {

    val random = Random
    if (options.isNotEmpty()) {
        // Selects a index within range of the list.
        val selectedIndex = random.nextInt(options.size)
        // Return the option at the specified index.
        return options[selectedIndex]
    }
    // Returns if no options have been given
    return "No options available"
}

// Function to calculate the font size based on the length of the decision
fun calculateFontSize(decision: String): Int {
    val length = decision.length
    if (length < 7) {
        return 65
    }
    return 55
}