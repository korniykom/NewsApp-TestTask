package com.korniykom.newsapp.presentation.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.korniykom.newsapp.presentation.components.NewsCard
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MainScreen"

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val articles = viewModel.articles.collectAsLazyPagingItems()


    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onAction(MainScreenAction.OnQueryChange(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for news...") },
            singleLine = true
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(
                count = articles.itemCount,
                key = articles.itemKey { it.url }
            ) { index ->
                articles[index]?.let { article ->
                    NewsCard(
                        article = article
                    )
                }
            }
        }
    }
}