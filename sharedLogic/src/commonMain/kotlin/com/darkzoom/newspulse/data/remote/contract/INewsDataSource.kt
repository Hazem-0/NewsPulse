package com.darkzoom.newspulse.data.remote.contract

import com.darkzoom.newspulse.data.remote.dto.NewsResponseDto


interface INewsDataSource {

    suspend fun getNews(query: String): Result<NewsResponseDto>
}
