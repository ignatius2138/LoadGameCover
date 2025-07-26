package com.example.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.practice.data.IGameCoverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

class GameCoversViewModel(val repository: IGameCoverRepository): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(2000)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collect { query ->
                    getImageUrl(query)
                }
        }
    }

    fun getImageUrl(gameName: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val url = repository.getImageUrl(gameName)
                _uiState.value = UiState.Success(url ?: "")
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }

    fun onQueryChanged(newText: String) {
        searchQuery.value = newText
    }
}

sealed interface UiState {
    data class Success(val url: String): UiState
    data class Error(val errorText: String?): UiState
    object Loading: UiState
}

class GameCoversViewModelFactory(
    private val repository: IGameCoverRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameCoversViewModel::class.java)) {
            return GameCoversViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}