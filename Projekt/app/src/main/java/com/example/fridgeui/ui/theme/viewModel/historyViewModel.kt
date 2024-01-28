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

class HistoryViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(application) as T
    }
}

class HistoryRepository(historyDaoo: HistoryDao) {
    private val historyDao: HistoryDao = historyDaoo

    fun getHistory(): Flow<List<HistoryElement>> {
        return historyDao.getHistory()
    }

    suspend fun insert(history: HistoryElement) {
        historyDao.insert(history)
    }

    suspend fun deleteAll() {
        historyDao.deleteAll()
    }

    suspend fun update(history: HistoryElement) {
        historyDao.update(history)
    }

    suspend fun delete(history: HistoryElement) {
        historyDao.delete(history)
    }
}
class HistoryViewModel(application: Application) : ViewModel() {

    private val repository: HistoryRepository
    private val _historyState = MutableStateFlow<List<HistoryElement>>(emptyList())
    val usersState: StateFlow<List<HistoryElement>>
        get() = _historyState

    init {
        val db = HistoryDatabase.getDatabase(application)
        val dao = db.historyDao()
        repository = HistoryRepository(dao)

        fetchHistory()
    }

    private fun fetchHistory() {
        viewModelScope.launch {
            repository.getHistory().collect { history ->
                _historyState.value = history
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
    fun deleteHistoryElement(history: HistoryElement) {
        viewModelScope.launch {
            repository.delete(history)
        }
    }

    fun addHistoryElement(history: HistoryElement) {
        viewModelScope.launch {
            repository.insert(history)
        }
    }

    fun updateHistoryElement(history: HistoryElement) {
        viewModelScope.launch {
            repository.update(history)
        }
    }
}