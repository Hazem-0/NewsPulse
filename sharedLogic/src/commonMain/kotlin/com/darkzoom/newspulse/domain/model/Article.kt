package com.darkzoom.newspulse.domain.model


data class Article(
    val sourceId: String?,
    val sourceName: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishDate: String,
    val content: String?,
    val isFavorite: Boolean
)