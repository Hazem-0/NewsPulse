package com.darkzoom.newspulse.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzoom.newspulse.view.home.components.NewsItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val newsItems: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface HomeEffect {
    data class NavigateToDetails(val newsItem: NewsItem) : HomeEffect
}

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = Channel<HomeEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        loadNews()
    }

    private fun loadNews() {
        _state.update { it.copy(isLoading = true) }
        val mockData = listOf(
            NewsItem(
                imageUrl = "https://picsum.photos/800/400?random=1",
                title = "Tech Giants Unveil New AI Breakthrough at Annual Conference",
                description = "Industry leaders showcased advancements...",
                date = "June 17, 2026"
            )
        )
        _state.update { it.copy(newsItems = mockData, isLoading = false) }
    }

    fun onNewsClick(newsItem: NewsItem) {
        viewModelScope.launch {
            _effect.send(HomeEffect.NavigateToDetails(newsItem))
        }
    }
}
