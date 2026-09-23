package com.korniykom.newsapp.data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.korniykom.newsapp.data.local.ArticleEntity
import com.korniykom.newsapp.data.local.NewsDatabase
import com.korniykom.newsapp.data.mappers.toEntity
import com.korniykom.newsapp.data.networking.UrlConstants.BASE_URL
import com.korniykom.newsapp.data.remote.dto.NewsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val query: String,
    private val database: NewsDatabase,
    private val httpClient: HttpClient,
) : RemoteMediator<Int, ArticleEntity>() {
    private val articleDao = database.articleDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.page + 1
            }
        }

        Log.d(
            "NewsRemoteMediator",
            "loadType=$loadType requesting page=$page lastItem.page=${state.lastItemOrNull()?.page}"
        )

        return try {
            val response: NewsResponseDto = httpClient.get(BASE_URL) {
                parameter("q", query)
                parameter("page", page)
                parameter("pageSize", state.config.pageSize)
            }.body()

            val articles = response.articles.map { it.toEntity(query = query, page = page) }
            Log.d(
                "NewsRemoteMediator",
                "page=$page returned ${articles.size} articles, totalResults=${response.totalResults}"
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDao.clearForQuery(query)
                }
                articleDao.upsertAll(articles)
            }

            MediatorResult.Success(endOfPaginationReached = articles.isEmpty())
        } catch (e: Exception) {
            Log.e("NewsRemoteMediator", "load failed loadType=$loadType page=$page", e)
            MediatorResult.Error(e)
        }
    }
}