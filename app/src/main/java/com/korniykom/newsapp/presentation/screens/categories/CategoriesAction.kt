package com.korniykom.newsapp.presentation.screens.categories

import com.korniykom.newsapp.domain.model.NewsCategory

sealed interface CategoriesAction {
    data class OnCategorySelected(val category: NewsCategory) : CategoriesAction
    data class OnDropdownExpandedChange(val expanded: Boolean) : CategoriesAction
}