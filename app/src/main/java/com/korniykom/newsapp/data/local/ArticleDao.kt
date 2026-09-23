package com.korniykom.newsapp.data.local


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticleDao {

    @Upsert
    suspend fun upsertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE query = :query ORDER BY page, url")
    fun pagingSource(query: String): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles WHERE query = :query")
    suspend fun clearForQuery(query: String)
}