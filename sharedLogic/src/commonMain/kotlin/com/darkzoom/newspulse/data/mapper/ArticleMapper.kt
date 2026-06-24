package com.darkzoom.newspulse.data.mapper

import com.darkzoom.newspulse.data.local.entity.ArticleEntity
import com.darkzoom.newspulse.data.remote.dto.ArticleDto
import com.darkzoom.newspulse.domain.model.Article

fun ArticleDto.toEntity() = ArticleEntity(
    url = url,
    sourceId = source.id,
    sourceName = source.name,
    author = author,
    title = title,
    description = description,
    imageUrl = imageUrl,
    publishDate = publishDate,
    content = content,
    isFavorite = false
)

fun ArticleEntity.toDomain() = Article(
    sourceId = sourceId,
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    url = url,
    imageUrl = imageUrl,
    publishDate = publishDate,
    content = content,
    isFavorite = isFavorite
)