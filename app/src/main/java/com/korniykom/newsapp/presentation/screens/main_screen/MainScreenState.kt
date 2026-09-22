package com.korniykom.newsapp.presentation.screens.main_screen

data class MainScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = true,
)