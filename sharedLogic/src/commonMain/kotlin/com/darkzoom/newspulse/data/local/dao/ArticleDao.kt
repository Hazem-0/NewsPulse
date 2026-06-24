package com.darkzoom.newspulse.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.darkzoom.newspulse.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles ")
    fun getArticlesByQuery(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<ArticleEntity>>

    @Query("UPDATE articles SET isFavorite = :isFavorite WHERE url = :url")
    suspend fun setFavorite(url: String, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArticles(articles: List<ArticleEntity>)
}