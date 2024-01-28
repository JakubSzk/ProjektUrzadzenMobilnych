package com.example.fridgeui.ui.theme.view

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.fridgeui.globalProduct
import com.example.fridgeui.ui.theme.model.HistoryElement
import com.example.fridgeui.ui.theme.model.Product
import com.example.fridgeui.ui.theme.model.Screens
import com.example.fridgeui.ui.theme.viewModel.HistoryViewModel
import com.example.fridgeui.ui.theme.viewModel.HistoryViewModelFactory
import com.example.fridgeui.ui.theme.viewModel.ProductViewModel
import com.example.fridgeui.ui.theme.viewModel.ProductViewModelFactory

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductEditScreen(bottomPadding: Dp, navController: NavHostController)
{
    var productAmount by remember { mutableStateOf("0.0") }
    val viewModel: ProductViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        ProductViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val viewModel2: HistoryViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel2",
        HistoryViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding)
    ) {

        Text(
            text = globalProduct.product,
            fontSize = 32.sp
        )

        TextField(
            value = productAmount,
            onValueChange = { productAmount = it },
            label = { Text("Ilość") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val amount = productAmount.toFloatOrNull() ?: 0.0f


                if (globalProduct.amount > amount) {
                    globalProduct.amount -= amount
                    viewModel.updateProduct(globalProduct)
                    viewModel2.addHistoryElement(HistoryElement(0, globalProduct.product, -amount))
                }
                else {
                    viewModel.deleteProduct(globalProduct)
                    viewModel2.addHistoryElement(HistoryElement(0, globalProduct.product, -globalProduct.amount))
                }
                productAmount = "0.0"
                navController.navigate(Screens.ProductListScreen.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("UPDATE")
        }
        Button(
            onClick = {
                viewModel.deleteProduct(globalProduct)
                viewModel2.addHistoryElement(HistoryElement(0, globalProduct.product, -globalProduct.amount))
                navController.navigate(Screens.ProductListScreen.route)
            },
            modifier = Modifier.fillMaxWidth()

        )
        {
            Text(text = "All")
        }
    }
}