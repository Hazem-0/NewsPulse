package com.darkzoom.newspulse.screens.home.viewmodel

import com.darkzoom.newspulse.domain.model.Article

sealed interface HomeUIState {
    data object Loading : HomeUIState
    data class Success(val news: List<Article>) : HomeUIState
    data class Error(val message: String) : HomeUIState
}

sealed interface HomeUIEffect {
    data class ShowToast(val message: String) : HomeUIEffect
}

sealed interface HomeUIEvent {
    data object FetchNews : HomeUIEvent
    data class ToggleFavorite(val url: String, val isFavorite: Boolean) : HomeUIEvent
}