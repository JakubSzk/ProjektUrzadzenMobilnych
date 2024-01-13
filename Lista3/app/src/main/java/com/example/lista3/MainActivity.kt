package com.example.lista3


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.lista3.ui.theme.Lista3Theme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lista3.model.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

val questions = QestionData()
var points:Int = 0
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questions.refreshData(10);
        setContent {
            Lista3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}
sealed class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object SecondScreen : Screens("second_screen")
}

@Composable
fun MainScreen(onSecondScreen: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {
        var questionTracker by rememberSaveable {
            mutableStateOf(1)
        }
        var questionValue: MutableState<String> = rememberSaveable {
            mutableStateOf(questions.questions[questionTracker - 1].questionText)
        }
        var questionAnswers: MutableState<List<String>> = rememberSaveable {
            mutableStateOf(listOf(questions.questions[questionTracker- 1].ans1, questions.questions[questionTracker- 1].ans2, questions.questions[questionTracker- 1].ans3, questions.questions[questionTracker- 1].ans4))
        }
        val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(questionAnswers.value[1] ) }
        Text(
            text = "Pytanie ${questionTracker}/10",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 60.dp)
        )
        LinearProgressIndicator(
            progress = (questionTracker * 0.1).toFloat(),
            modifier = Modifier.padding(top = 35.dp, bottom = 40.dp),
            color = colorResource(id = R.color.greenish))
        Text(
            text ="${questionValue.value}",
            fontSize = 25.sp,
            modifier = Modifier
                .background(colorResource(id = R.color.light_gray))
                .padding(top = 20.dp, bottom = 20.dp)
        )
        Column (
            Modifier
                .background(color = colorResource(id = R.color.yellowish))
                .padding(top = 20.dp, bottom = 20.dp)
        ){
            questionAnswers.value.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .background(colorResource(id = R.color.light_gray))
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 25.sp
                    )
                }
            }
        }
        Button(
            onClick = {
                if(questionAnswers.value.indexOf(selectedOption) + 1 == questions.questions[questionTracker - 1].correctOptionIndex)
                    points++
                if(questionTracker < 10)
                {
                    questionTracker++
                    questionValue.value = questions.questions[questionTracker - 1].questionText
                    questionAnswers.value = listOf(questions.questions[questionTracker- 1].ans1, questions.questions[questionTracker- 1].ans2, questions.questions[questionTracker- 1].ans3, questions.questions[questionTracker- 1].ans4)
                }
                else
                    onSecondScreen() },
            modifier = Modifier
                .fillMaxSize()

        ) {
            Text(
                text = "Następne",
                fontSize = 25.sp
            )
        }
    }
}
@Composable
fun SecondScreen(onHome: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Gratulacje",
            fontSize = 35.sp,
            modifier = Modifier.padding(top = 60.dp, bottom = 60.dp)
            )
        Text(
            text = "\n\n\n\n\n\n\n\n\n\nZdobyłeś ${points*10} pkt",
            fontSize = 35.sp,
            modifier = Modifier.fillMaxSize()
                .background(colorResource(id = R.color.yellowish)),
            textAlign = TextAlign.Center
            )
        
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(route = Screens.MainScreen.route){
            MainScreen{navController.navigate(Screens.SecondScreen.route)}
        }

        composable(route = Screens.SecondScreen.route){
            SecondScreen {navController.popBackStack()}
        }
    }
}