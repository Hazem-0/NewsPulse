package com.darkzoom.newspulse.domain.usecase

import com.darkzoom.newspulse.domain.repository.INewsRepository

class FetchNewsUseCase(
    private val repository: INewsRepository
) {
    suspend operator fun invoke(query: String): Result<Unit> {
        return repository.fetchNews(query)
    }
}