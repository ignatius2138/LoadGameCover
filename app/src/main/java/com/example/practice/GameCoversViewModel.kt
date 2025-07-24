package com.example.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.data.IGameCoverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameCoversViewModel(val repository: IGameCoverRepository): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getImageUrl(gameText: String) {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val url = repository.getImageUrl(gameText)
                _uiState.value = UiState.Success(url)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }
}

sealed interface UiState {
    data class Success(val url: String): UiState
    data class Error(val errorText: String?): UiState
    object Loading: UiState
}