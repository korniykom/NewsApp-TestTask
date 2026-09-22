package com.korniykom.newsapp.presentation.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainScreenViewModel : ViewModel() {
    private var hasStartedSearchListener = false
    private val _state = MutableStateFlow(MainScreenState())
    private val searchQueryState = _state
        .map { it.searchQuery }
        .debounce(400)
        .distinctUntilChanged()
        .onEach { query ->
            if (query.isNotBlank()) {
                search(query)
            }
        }
    val state = _state
        .onStart {
            if (!hasStartedSearchListener) {
                searchQueryState.launchIn(viewModelScope)
                hasStartedSearchListener = true
            }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainScreenState()
        )

    fun onAction(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.OnQueryChange -> onQueryChange(action.text)
        }
    }

    private fun search(query: String) {
        _state.update { it.copy(isLoading = true) }

        _state.update { it.copy(isLoading = false) }
    }

    private fun onQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

}