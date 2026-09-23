package com.korniykom.newsapp.presentation.screens.main_screen

sealed interface MainScreenAction {
    data class OnQueryChange(val text: String) : MainScreenAction
}