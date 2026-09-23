package com.korniykom.newsapp.domain.repository

import androidx.paging.PagingData
import com.korniykom.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun searchArticles(query: String): Flow<PagingData<Article>>
}