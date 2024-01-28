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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.room.util.TableInfo
import com.example.fridgeui.globalProduct
import com.example.fridgeui.ui.theme.Green
import com.example.fridgeui.ui.theme.LightGreen
import com.example.fridgeui.ui.theme.model.Product
import com.example.fridgeui.ui.theme.model.Screens
import com.example.fridgeui.ui.theme.viewModel.ProductViewModel
import com.example.fridgeui.ui.theme.viewModel.ProductViewModelFactory

@Composable
fun ProductListScreen(bottomPadding: Dp, navController: NavHostController)
{
    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val products by viewModel.usersState.collectAsStateWithLifecycle()

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
            items(products.size) {
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
                        .clickable {
                            globalProduct = products[it]
                            navController.navigate(Screens.ProductEditScreen.route)
                        }
                ) {
                    Column (
                        horizontalAlignment = Alignment.Start,
                       // modifier = Modifier.background(LightGreen)
                    ) {
                        Text(text = products[it].product,
                            fontSize = 32.sp,
                            )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column (
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.background(Green)
                    ) {
                        Text(text = "${products[it].amount}",
                            fontSize = 32.sp,
                            )
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate(Screens.ProductAddScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = "ADD")
        }
    }
}