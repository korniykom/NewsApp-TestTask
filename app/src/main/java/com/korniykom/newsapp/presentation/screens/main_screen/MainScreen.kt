package com.korniykom.newsapp.presentation.screens.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { viewModel.onAction(MainScreenAction.OnQueryChange(it))},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for news...")},
            singleLine = true
        )
    }
}