package com.example.fridgeui.ui.theme.model

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fridgeui.ui.theme.view.HistoryScreen
import com.example.fridgeui.ui.theme.view.ProductAddScreen
import com.example.fridgeui.ui.theme.view.ProductEditScreen
import com.example.fridgeui.ui.theme.view.ProductListScreen

sealed class Screens(val route: String) {
    object ProductListScreen : Screens("product_list")
    object ProductEditScreen : Screens("product_edit")
    object ProductAddScreen : Screens("product_add")
    object HistoryScreen : Screens("history")
}

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBar(Screens.ProductListScreen.route, "Fridge", Icons.Default.Home)
    object First : BottomBar(Screens.HistoryScreen.route, "History", Icons.Default.Info)
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
        startDestination = Screens.ProductListScreen.route

    ) {
        composable(route = Screens.ProductListScreen.route){ ProductListScreen(bottomPadding, navController = navController) }
        composable(route = Screens.ProductEditScreen.route){ ProductEditScreen(bottomPadding, navController = navController ) }
        composable(route = Screens.ProductAddScreen.route){ ProductAddScreen(bottomPadding) }
        composable(route = Screens.HistoryScreen.route) { HistoryScreen(bottomPadding)}
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