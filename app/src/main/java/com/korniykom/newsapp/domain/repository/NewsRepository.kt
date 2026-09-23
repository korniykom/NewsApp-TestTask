package com.korniykom.newsapp.domain.repository

import androidx.paging.PagingData
import com.korniykom.newsapp.domain.model.Article
import com.korniykom.newsapp.domain.model.NewsCategory
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun searchArticles(query: String): Flow<PagingData<Article>>
    fun getTopHeadlines(category: NewsCategory): Flow<PagingData<Article>>
}