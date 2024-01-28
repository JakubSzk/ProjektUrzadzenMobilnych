package com.example.fridgeui.ui.theme.view
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fridgeui.ui.theme.FridgeUITheme
import com.example.fridgeui.ui.theme.model.HistoryElement
import com.example.fridgeui.ui.theme.model.Product
import com.example.fridgeui.ui.theme.model.Screens
import com.example.fridgeui.ui.theme.viewModel.HistoryViewModel
import com.example.fridgeui.ui.theme.viewModel.HistoryViewModelFactory
import com.example.fridgeui.ui.theme.viewModel.ProductViewModel
import com.example.fridgeui.ui.theme.viewModel.ProductViewModelFactory
import kotlinx.coroutines.flow.collect
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductAddScreen(bottomPadding: Dp)  {

    var productName by remember { mutableStateOf("") }
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

        TextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Nazwa") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    keyboardController?.show()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
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
                if (amount > 0 && productName != "") {
                    val product = Product(0, productName, amount)
                    //productRepository.insert(product)
                    viewModel.addProduct(product)
                    viewModel2.addHistoryElement(HistoryElement(0, productName, amount))
                    productName = ""
                    productAmount = "0.0"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ADD")
        }
    }



}