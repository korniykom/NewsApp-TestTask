package com.korniykom.newsapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object MainScreen: Route
    @Serializable
    data object Categories: Route
}