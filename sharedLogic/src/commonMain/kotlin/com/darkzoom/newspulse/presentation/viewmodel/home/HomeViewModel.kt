package com.darkzoom.newspulse.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkzoom.newspulse.domain.usecase.GetNewUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getNewsUseCase: GetNewUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeUIEffect>()
    val uiEffect: SharedFlow<HomeUIEffect> = _uiEffect.asSharedFlow()

    init {
        onEvent(HomeUIEvent.FetchNews)
    }

    fun onEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.FetchNews -> fetchNews()
        }
    }

    private fun fetchNews() {
        _uiState.value = HomeUIState.Loading

        viewModelScope.launch {
            getNewsUseCase()
                .catch { exception ->
                    val errorMessage = exception.message ?: "An error occurred"
                    _uiState.value = HomeUIState.Error(errorMessage)
                    emitEffect(HomeUIEffect.ShowToast(errorMessage))
                }
                .collect { newsList ->
                    _uiState.value = HomeUIState.Success(newsList)
                }
        }
    }

    private fun emitEffect(effect: HomeUIEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}