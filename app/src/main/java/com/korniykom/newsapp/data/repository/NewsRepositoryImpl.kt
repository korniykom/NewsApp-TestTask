package com.korniykom.newsapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.korniykom.newsapp.data.local.NewsDatabase
import com.korniykom.newsapp.data.mappers.toDomain
import com.korniykom.newsapp.data.remote.NewsRemoteMediator
import com.korniykom.newsapp.domain.model.Article
import com.korniykom.newsapp.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val httpClient: HttpClient,
    private val database: NewsDatabase
) : NewsRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun searchArticles(query: String): Flow<PagingData<Article>> {
        val pagingSourceFactory = { database.articleDao().pagingSource(query) }

        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = NewsRemoteMediator(query, database, httpClient),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}