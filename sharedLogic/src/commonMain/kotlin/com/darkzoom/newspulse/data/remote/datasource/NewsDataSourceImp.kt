package com.darkzoom.newspulse.data.remote.datasource

import com.darkzoom.newspulse.data.remote.contract.INewsDataSource
import com.darkzoom.newspulse.data.remote.dto.NewsResponseDto
import com.darkzoom.newspulse.data.remote.utils.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsDataSourceImp(private val httpClient: HttpClient) : INewsDataSource {

    override suspend fun getNews(query: String): Result<NewsResponseDto> {
        return try {
            val response: NewsResponseDto = httpClient.get("${NetworkConfig.BASE_URL}/everything") {
                parameter("q", query)
                parameter("sortBy", "publishedAt")
                parameter("apiKey", NetworkConfig.API_KEY)
            }.body()
            Result.success(response)
        } catch (e: ClientRequestException) {
            Result.failure(Exception("Client error: ${e.response.status.description}"))
        } catch (e: ServerResponseException) {
            Result.failure(Exception("Server error: ${e.response.status.description}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}