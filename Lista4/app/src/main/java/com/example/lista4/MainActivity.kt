package com.example.lista4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lista4.data.*
import com.example.lista4.ui.theme.Lista4Theme

val createdData = Generator()
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createdData.refreshData(20)
        setContent {
            Lista4Theme {
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
    object HomeScreen : Screens("home")
    object FirstScreen : Screens("first")
    object SecondScreen : Screens("second")
}

@Composable
fun HomeScreen(bottomPadding: Dp, navController: NavHostController){
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(bottom = bottomPadding),
        //contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Moje listy zadań")
            LazyColumn {
                items(createdData.listOfLists.size) { item ->
                    var itemek by remember {
                        mutableStateOf(item)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(color = colorResource(id = R.color.yellowish))
                            .clickable {
                                createdData.klikniety = item
                                navController.navigate(Screens.SecondScreen.route)
                            }
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = createdData.listOfLists[item].subject.subjectName)
                            Text(text = "Liczba zadań: ${createdData.listOfLists[item].exercises.size}")
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Spacer, aby oddzielić kolumny
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Lista: ${createdData.listOfLists[item].nrLis}")
                            Text(text = "Ocena: " + String.format("%.2f", createdData.listOfLists[item].grade))
                        }
                    }
                }
            }
        }
        
    }
}

@Composable
fun FirstScreen(bottomPadding: Dp) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(bottom = bottomPadding)
        //contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Moje Oceny")
            LazyColumn {
                items(createdData.listOfResults.size) { item ->
                    var itemek by remember {
                        mutableStateOf(item)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(color = colorResource(id = R.color.yellowish))
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(text = createdData.listOfResults[item].subject.subjectName)
                            Text(text = "Liczba list: ${createdData.listOfResults[item].amount}")
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Spacer, aby oddzielić kolumny
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Średnia: " + String.format("%.2f", createdData.listOfResults[item].avg))
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SecondScreen(bottomPadding: Dp){
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(bottom = bottomPadding)
        //contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = createdData.listOfLists[createdData.klikniety].subject.subjectName)
            Text(text = "Lista ${createdData.listOfLists[createdData.klikniety].nrLis}")
            LazyColumn {
                items(createdData.listOfLists[createdData.klikniety].exercises.size) { item ->
                    var itemek by remember {
                        mutableStateOf(item)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .background(color = colorResource(id = R.color.yellowish))
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Spacer(modifier = Modifier.weight(0.1f))
                            Text(text = "Zadanie ${item + 1}")
                            Text(text = createdData.listOfLists[createdData.klikniety].exercises[item].content)
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Spacer, aby oddzielić kolumny
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "pkt: ${createdData.listOfLists[createdData.klikniety].exercises[item].points}" )
                        }
                    }
                }
            }
        }

    }
}

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBar(Screens.HomeScreen.route, "Home", Icons.Default.Home)
    object First : BottomBar(Screens.FirstScreen.route, "First", Icons.Default.Info)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomMenu(navController = navController)},
        content = { BottomNavGraph(navController = navController, bottomPadding = it.calculateBottomPadding()) }
    )
}

@Composable
fun BottomNavGraph(navController: NavHostController, bottomPadding: Dp){
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(route = Screens.HomeScreen.route){ HomeScreen(bottomPadding, navController = navController)}
        composable(route = Screens.FirstScreen.route){ FirstScreen(bottomPadding) }
        composable(route = Screens.SecondScreen.route){ SecondScreen(bottomPadding) }
    }
}

@Composable
fun BottomMenu(navController: NavHostController){
    val screens = listOf(
        BottomBar.Home, BottomBar.First
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar{
        screens.forEach{screen ->
            NavigationBarItem(
                label = { Text(text = screen.title)},
                icon = {Icon(imageVector = screen.icon, contentDescription = "icon")},
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {navController.navigate(screen.route)}
            )
        }
    }
}
