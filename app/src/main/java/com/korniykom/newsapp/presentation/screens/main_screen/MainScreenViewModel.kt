package com.korniykom.newsapp.presentation.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.korniykom.newsapp.domain.model.Article
import com.korniykom.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class MainScreenViewModel(
    private val repository: NewsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    val articles: Flow<PagingData<Article>> = _state
        .map { it.searchQuery }
        .debounce(400)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                repository.searchArticles(query)
            }
        }
        .cachedIn(viewModelScope)


    fun onAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.OnQueryChange -> onQueryChange(action.text)
        }
    }


    private fun onQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

}