package com.darkzoom.newspulse.domain.usecase

import com.darkzoom.newspulse.domain.model.News
import com.darkzoom.newspulse.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewUseCase (private val newsRepository: INewsRepository) {

    operator fun invoke(): Flow<List<News>> {
        return  newsRepository.fetchNews()
    }


}