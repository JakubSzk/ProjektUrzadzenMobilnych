package com.example.fridgeui.ui.theme.view

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgeui.globalProduct
import com.example.fridgeui.ui.theme.Green
import com.example.fridgeui.ui.theme.LightGreen
import com.example.fridgeui.ui.theme.model.Screens
import com.example.fridgeui.ui.theme.viewModel.HistoryViewModel
import com.example.fridgeui.ui.theme.viewModel.HistoryViewModelFactory
import com.example.fridgeui.ui.theme.viewModel.ProductViewModel
import com.example.fridgeui.ui.theme.viewModel.ProductViewModelFactory

@Composable
fun HistoryScreen(bottomPadding: Dp)
{
    val viewModel2: HistoryViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel2",
        HistoryViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val historyElements by viewModel2.usersState.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = bottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)

        ) {
            items(historyElements.size) {
//                Text(
//                    text = "${products[it].product} ${products[it].amount}",
//                    fontSize = 32.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(2.dp)
//                )
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(LightGreen)
                ) {
                    Column (
                        horizontalAlignment = Alignment.Start,
                        // modifier = Modifier.background(LightGreen)
                    ) {
                        Text(text = historyElements[it].product,
                            fontSize = 32.sp,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column (
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.background(Green)
                    ) {
                        Text(text = "${historyElements[it].amount}",
                            fontSize = 32.sp,
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                viewModel2.clearHistory() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = "CLEAR")
        }
    }
}