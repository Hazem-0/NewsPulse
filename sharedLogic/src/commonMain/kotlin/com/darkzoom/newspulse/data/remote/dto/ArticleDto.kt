package com.darkzoom.newspulse.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    @SerialName("source")
    val source: SourceDto,

    @SerialName("author")
    val author: String?,

    @SerialName("title")
    val title: String,

    @SerialName("description")
    val description: String?,

    @SerialName("url")
    val url: String,

    @SerialName("urlToImage")
    val imageUrl: String?,

    @SerialName("publishedAt")
    val publishDate: String,

    @SerialName("content")
    val content: String?
)