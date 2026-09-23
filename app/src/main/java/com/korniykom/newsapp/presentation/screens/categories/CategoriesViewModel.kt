package com.korniykom.newsapp.presentation.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.korniykom.newsapp.domain.model.Article
import com.korniykom.newsapp.domain.model.NewsCategory
import com.korniykom.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class CategoriesViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    val articles: Flow<PagingData<Article>> = _state
        .map { it.selectedCategory }
        .flatMapLatest { category -> repository.getTopHeadlines(category) }
        .cachedIn(viewModelScope)

    fun onAction(action: CategoriesAction) {
        when (action) {
            is CategoriesAction.OnCategorySelected -> onCategorySelected(action.category)
            is CategoriesAction.OnDropdownExpandedChange -> onDropdownExpandedChange(action.expanded)
        }
    }

    private fun onCategorySelected(category: NewsCategory) {
        _state.update { it.copy(selectedCategory = category, isDropdownExpanded = false) }
    }

    private fun onDropdownExpandedChange(expanded: Boolean) {
        _state.update { it.copy(isDropdownExpanded = expanded) }
    }
}