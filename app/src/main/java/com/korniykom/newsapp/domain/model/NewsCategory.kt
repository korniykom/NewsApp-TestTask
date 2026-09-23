package com.korniykom.newsapp.domain.model

enum class NewsCategory(val displayName: String, val apiValue: String?) {
    GENERAL("General", "general"),
    BUSINESS("Business", "business"),
    ENTERTAINMENT("Entertainment", "entertainment"),
    HEALTH("Health", "health"),
    SCIENCE("Science", "science"),
    SPORTS("Sports", "sports"),
    TECHNOLOGY("Technology", "technology")
}