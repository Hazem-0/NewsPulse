package com.darkzoom.newspulse.domain.repository

import com.darkzoom.newspulse.domain.model.Article
import com.darkzoom.newspulse.domain.model.News
import kotlinx.coroutines.flow.Flow

interface INewsRepository {
    suspend fun fetchNews(query: String): Result<Unit>

    fun getArticles(): Flow<List<Article>>
    fun getFavorites(): Flow<List<Article>>

    suspend fun toggleFavorite(url: String, isFavorite: Boolean)
}