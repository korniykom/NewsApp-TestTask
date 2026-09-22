package com.korniykom.newsapp.presentation.navigation

import androidx.annotation.DrawableRes
import com.korniykom.newsapp.R

data class BottomNavItem(
    @DrawableRes val icon: Int,
    val title: String,
)

val APP_DESTINATIONS = mapOf(
    Route.MainScreen to BottomNavItem(
        icon = R.drawable.news,
        title = "News"
    ),
    Route.Categories to BottomNavItem(
        icon = R.drawable.news_search,
        title = "Categories"
    )
)