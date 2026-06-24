package com.darkzoom.newspulse.domain.usecase

import com.darkzoom.newspulse.domain.repository.INewsRepository

class ToggleFavoriteUseCase(
    private val repository: INewsRepository
) {
    suspend operator fun invoke(url: String, isFavorite: Boolean) {
        repository.toggleFavorite(url, isFavorite)
    }
}