package com.darkzoom.newspulse.domain.usecase

import com.darkzoom.newspulse.domain.model.Article
import com.darkzoom.newspulse.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteArticlesUseCase(
    private val repository: INewsRepository
) {
    operator fun invoke(): Flow<List<Article>> {
        return repository.getFavorites()
    }
}