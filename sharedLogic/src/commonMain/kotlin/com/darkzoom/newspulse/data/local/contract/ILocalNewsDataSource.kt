package com.darkzoom.newspulse.data.local.contract

import com.darkzoom.newspulse.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ILocalNewsDataSource {
    fun getArticles(): Flow<List<ArticleEntity>>
    fun getFavorites(): Flow<List<ArticleEntity>>
    suspend fun setFavorite(url: String, isFavorite: Boolean)
    suspend fun insertArticles(articles: List<ArticleEntity>)
}