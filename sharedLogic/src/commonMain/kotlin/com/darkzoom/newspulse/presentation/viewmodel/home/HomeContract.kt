package com.darkzoom.newspulse.presentation.viewmodel.home

import com.darkzoom.newspulse.domain.model.News

sealed interface HomeUIState {
    data object Loading : HomeUIState
    data class Success(val news: List<News>) : HomeUIState
    data class Error(val message: String) : HomeUIState
}

sealed interface HomeUIEffect {
    data class ShowToast(val message: String) : HomeUIEffect
}

sealed interface HomeUIEvent {
     object FetchNews : HomeUIEvent
}
