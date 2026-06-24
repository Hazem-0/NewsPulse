package com.darkzoom.newspulse.data.local.datasource

import com.darkzoom.newspulse.data.local.contract.ILocalNewsDataSource
import com.darkzoom.newspulse.data.local.dao.ArticleDao
import com.darkzoom.newspulse.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

class LocalNewsDataSourceImp(
    private val articleDao: ArticleDao
) : ILocalNewsDataSource {

    override fun getArticles(): Flow<List<ArticleEntity>> {
        return articleDao.getArticlesByQuery()
    }

    override fun getFavorites(): Flow<List<ArticleEntity>> {
        return articleDao.getFavorites()
    }

    override suspend fun setFavorite(url: String, isFavorite: Boolean) {
        articleDao.setFavorite(url, isFavorite)
    }

    override suspend fun insertArticles(articles: List<ArticleEntity>) {
        articleDao.insertArticles(articles)
    }
}