package com.darkzoom.newspulse.screens.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzoom.newspulse.domain.usecase.GetFavoriteArticlesUseCase
import com.darkzoom.newspulse.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoritesUIState>(FavoritesUIState.Loading)
    val uiState: StateFlow<FavoritesUIState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<FavoritesUIEffect>()
    val uiEffect: SharedFlow<FavoritesUIEffect> = _uiEffect.asSharedFlow()

    init {
        observeFavorites()
    }

    fun onEvent(event: FavoritesUIEvent) {
        when (event) {
            is FavoritesUIEvent.RemoveFavorite -> removeFavorite(event.url)
        }
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteArticlesUseCase().collect { newsList ->
                _uiState.value = FavoritesUIState.Success(newsList)
            }
        }
    }

    private fun removeFavorite(url: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(url, false)
        }
    }
}