package com.darkzoom.newspulse.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzoom.newspulse.domain.usecase.FetchNewsUseCase
import com.darkzoom.newspulse.domain.usecase.GetArticlesUseCase
import com.darkzoom.newspulse.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUIEffect>()
    val uiEffect: SharedFlow<HomeUIEffect> = _uiEffect.asSharedFlow()

    init {
        observeArticles()
        fetchNews("general")
    }

    fun onEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.FetchNews -> fetchNews("general")
            is HomeUIEvent.ToggleFavorite -> toggleFavorite(event.url, event.isFavorite)
        }
    }

    private fun observeArticles() {
        viewModelScope.launch {
            getArticlesUseCase().collect { newsList ->
                if (newsList.isNotEmpty() || _uiState.value !is HomeUIState.Loading) {
                    _uiState.value = HomeUIState.Success(newsList)
                }
            }
        }
    }

    private fun fetchNews(query: String) {
        viewModelScope.launch {
            if (_uiState.value !is HomeUIState.Success) {
                _uiState.value = HomeUIState.Loading
            }

            fetchNewsUseCase(query).onFailure { exception ->
                val errorMessage = exception.message ?: "Unknown error"
                if (_uiState.value !is HomeUIState.Success) {
                    _uiState.value = HomeUIState.Error(errorMessage)
                }
                emitEffect(HomeUIEffect.ShowToast(errorMessage))
            }
        }
    }

    private fun toggleFavorite(url: String, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(url, isFavorite)
        }
    }

    private fun emitEffect(effect: HomeUIEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}