package com.darkzoom.newspulse.di

import com.darkzoom.newspulse.data.local.contract.ILocalNewsDataSource
import com.darkzoom.newspulse.data.local.dao.ArticleDao
import com.darkzoom.newspulse.data.local.datasource.LocalNewsDataSourceImp
import com.darkzoom.newspulse.data.local.utils.AppDatabase
import com.darkzoom.newspulse.data.remote.contract.INewsDataSource
import com.darkzoom.newspulse.data.remote.datasource.NewsDataSourceImp
import com.darkzoom.newspulse.data.repository.NewsRepositoryImp
import com.darkzoom.newspulse.domain.repository.INewsRepository
import com.darkzoom.newspulse.domain.usecase.FetchNewsUseCase
import com.darkzoom.newspulse.domain.usecase.GetArticlesUseCase
import com.darkzoom.newspulse.domain.usecase.GetFavoriteArticlesUseCase
import com.darkzoom.newspulse.domain.usecase.ToggleFavoriteUseCase
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {

    single {
        httpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
        }
    }

    single<ArticleDao> {
        get<AppDatabase>().articleDao()
    }

    single<ILocalNewsDataSource> { LocalNewsDataSourceImp(get()) }
    single<INewsDataSource> { NewsDataSourceImp(get()) }
    single<INewsRepository> { NewsRepositoryImp(get(), get()) }

    factory { FetchNewsUseCase(get()) }
    factory { GetArticlesUseCase(get()) }
    factory { GetFavoriteArticlesUseCase(get()) }
    factory { ToggleFavoriteUseCase(get()) }



}