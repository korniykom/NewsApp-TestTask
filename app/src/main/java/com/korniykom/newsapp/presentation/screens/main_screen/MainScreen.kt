package com.korniykom.newsapp.presentation.screens.main_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MainScreen"

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val articles = viewModel.articles.collectAsLazyPagingItems()

    LaunchedEffect(articles.loadState) {
        Log.d(TAG, "loadState: ${articles.loadState}")
    }

    LaunchedEffect(articles.itemCount) {
        Log.d(TAG, "itemCount: ${articles.itemCount}")
    }

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
                    Text(article.title, modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}