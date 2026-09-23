package com.korniykom.newsapp.data.mappers

import com.korniykom.newsapp.data.remote.dto.ArticleDto
import com.korniykom.newsapp.data.local.ArticleEntity
import com.korniykom.newsapp.domain.model.Article


fun ArticleDto.toDomain() = Article(
    sourceName = source.name,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun ArticleDto.toEntity(query: String, page: Int) = ArticleEntity(
    url = url,
    query = query,
    page = page,
    sourceId = source.id,
    sourceName = source.name,
    author = author,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)

fun ArticleEntity.toDomain() = Article(
    sourceName = sourceName,
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    content = content
)