package com.example.practice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameCoversViewModel: ViewModel() {
    private val _UiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _UiState.asStateFlow()

    fun loadCover() {

    }
}

sealed interface UiState {
    data class Success(val url: String): UiState
    data class Error(val errorText: String): UiState
    object Loading: UiState
}