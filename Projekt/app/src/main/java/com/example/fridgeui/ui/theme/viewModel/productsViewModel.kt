package com.example.fridgeui.ui.theme.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fridgeui.ui.theme.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(application) as T
    }
}

class ProductRepository(productDaoo: ProductDao) {
    private val productDao: ProductDao = productDaoo

    fun getProducts(): Flow<List<Product>> {
        return productDao.getProducts()
    }

    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    suspend fun deleteAll() {
        productDao.deleteAll()
    }

    suspend fun update(product: Product) {
        productDao.update(product)
    }

    suspend fun delete(product: Product) {
        productDao.delete(product)
    }
}
class ProductViewModel(application: Application) : ViewModel() {

    private val repository: ProductRepository
    private val _productsState = MutableStateFlow<List<Product>>(emptyList())
    val usersState: StateFlow<List<Product>>
        get() = _productsState

    init {
        val db = ProductDatabase.getDatabase(application)
        val dao = db.productDao()
        repository = ProductRepository(dao)

        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getProducts().collect { products ->
                _productsState.value = products
            }
        }
    }

    fun clearProducts() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.delete(product)
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.update(product)
        }
    }
}