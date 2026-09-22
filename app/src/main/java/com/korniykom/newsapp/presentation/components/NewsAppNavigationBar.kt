package com.korniykom.newsapp.presentation.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation3.runtime.NavKey
import com.korniykom.newsapp.presentation.navigation.APP_DESTINATIONS

@Composable
fun NewAppNavigationBar(
    selectedKey: NavKey,
    onSelectKey: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        APP_DESTINATIONS.forEach { (destination, data) ->
            NavigationBarItem(
                selected = destination == selectedKey,
                onClick = { onSelectKey(destination) },
                icon = {
                    Icon(
                        painter = painterResource(id = data.icon),
                        contentDescription = data.title
                    )
                },
                label = {
                    Text(data.title)
                }
            )
        }
    }
}