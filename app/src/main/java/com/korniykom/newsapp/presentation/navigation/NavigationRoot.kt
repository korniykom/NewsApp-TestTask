package com.korniykom.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.korniykom.newsapp.presentation.screens.categories.CategoriesScreen
import com.korniykom.newsapp.presentation.screens.main_screen.MainScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.MainScreen)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Route.MainScreen> {
                MainScreen()
            }
            entry<Route.Categories> {
                CategoriesScreen()
            }
        }
    )
}