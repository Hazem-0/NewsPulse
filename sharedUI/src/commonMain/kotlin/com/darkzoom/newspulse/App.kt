package com.darkzoom.newspulse

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.darkzoom.newspulse.view.home.HomeScreen
import com.darkzoom.newspulse.view.home.components.NewsItem

@Composable
fun App() {
    MaterialTheme {
        HomeScreen(
            onNavigateToDetails = { newsItem: NewsItem ->

            }
        )
    }
}