package com.darkzoom.newspulse.data.repository

import com.darkzoom.newspulse.data.local.contract.ILocalNewsDataSource
import com.darkzoom.newspulse.data.mapper.toDomain
import com.darkzoom.newspulse.data.mapper.toEntity
import com.darkzoom.newspulse.data.remote.contract.INewsDataSource
import com.darkzoom.newspulse.domain.model.Article
import com.darkzoom.newspulse.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImp(
    private val remoteDataSource: INewsDataSource,
    private val localDataSource: ILocalNewsDataSource
) : INewsRepository {

    override suspend fun fetchNews(query: String): Result<Unit> {
        return remoteDataSource.getNews(query).map { dto ->
            val entities = dto.articles.map { it.toEntity() }
            localDataSource.insertArticles(entities)
        }
    }

    override fun getArticles(): Flow<List<Article>> {
        return localDataSource.getArticles().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFavorites(): Flow<List<Article>> {
        return localDataSource.getFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(url: String, isFavorite: Boolean) {
        localDataSource.setFavorite(url, isFavorite)
    }
}