package com.korniykom.newsapp.presentation.screens.categories

import com.korniykom.newsapp.domain.model.NewsCategory

data class CategoriesState(
    val selectedCategory: NewsCategory = NewsCategory.GENERAL,
    val isDropdownExpanded: Boolean = false
)