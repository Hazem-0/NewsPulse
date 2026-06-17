package com.darkzoom.newspulse.domain.repository

import com.darkzoom.newspulse.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    fun fetchNews() : Flow<List<News>>
}