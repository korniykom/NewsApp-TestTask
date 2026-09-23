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
import com.korniykom.newsapp.domain.model.NewsCategory
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.JsonConvertException

@OptIn(ExperimentalPagingApi::class)
class TopHeadlinesRemoteMediator(
    private val category: NewsCategory,
    private val httpClient: HttpClient,
    private val database: NewsDatabase,
) : RemoteMediator<Int, ArticleEntity>() {

    private val articleDao = database.articleDao()
    private val cacheKey = category.apiValue ?: "all"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.page + 1
            }
        }

        return try {
            val response: NewsResponseDto = httpClient.get("$BASE_URL/top-headlines") {
                parameter("category", category.apiValue)
                parameter("page", page)
                parameter("pageSize", state.config.pageSize)
            }.body()

            val articles = response.articles.map { it.toEntity(query = cacheKey, page = page) }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    articleDao.clearForQuery(cacheKey)
                }
                articleDao.upsertAll(articles)
            }

            MediatorResult.Success(endOfPaginationReached = articles.isEmpty())
        } catch (e: JsonConvertException) {
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            Log.e("TopHeadlinesRemoteMediator", "load failed loadType=$loadType page=$page", e)
            MediatorResult.Error(e)
        }
    }
}