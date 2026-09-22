package com.korniykom.newsapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.korniykom.newsapp.presentation.components.NewAppNavigationBar
import com.korniykom.newsapp.presentation.screens.categories.CategoriesScreen
import com.korniykom.newsapp.presentation.screens.main_screen.MainScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.MainScreen)

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NewAppNavigationBar(
                selectedKey = backStack.last(),
                onSelectKey = { backStack.navigateToScreenAndRemoveExistingEntryFromBackStack(it) },
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
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
}

fun MutableList<NavKey>.navigateToScreenAndRemoveExistingEntryFromBackStack(entry: NavKey) {
    removeAll { it == entry }
    add(entry)
}