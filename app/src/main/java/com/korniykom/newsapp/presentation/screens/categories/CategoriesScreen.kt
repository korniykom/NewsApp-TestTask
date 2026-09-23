package com.korniykom.newsapp.presentation.screens.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.korniykom.newsapp.presentation.components.CategoryDropdown
import com.korniykom.newsapp.presentation.components.NewsCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val articles = viewModel.articles.collectAsLazyPagingItems()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CategoryDropdown(
            selectedCategory = state.selectedCategory,
            isExpanded = state.isDropdownExpanded,
            onExpandedChange = { viewModel.onAction(CategoriesAction.OnDropdownExpandedChange(it)) },
            onCategorySelected = { viewModel.onAction(CategoriesAction.OnCategorySelected(it)) }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = articles.itemCount,
                key = articles.itemKey { it.url }
            ) { index ->
                articles[index]?.let { article ->
                    NewsCard(article = article)
                }
            }
        }
    }
}
