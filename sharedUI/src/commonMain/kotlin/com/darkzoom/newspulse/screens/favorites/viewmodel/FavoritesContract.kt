package com.darkzoom.newspulse.screens.favorites.viewmodel

import com.darkzoom.newspulse.domain.model.Article

sealed interface FavoritesUIState {
    data object Loading : FavoritesUIState
    data class Success(val news: List<Article>) : FavoritesUIState
    data class Error(val message: String) : FavoritesUIState
}

sealed interface FavoritesUIEffect {
    data class ShowToast(val message: String) : FavoritesUIEffect
}

sealed interface FavoritesUIEvent {
    data class RemoveFavorite(val url: String) : FavoritesUIEvent
}