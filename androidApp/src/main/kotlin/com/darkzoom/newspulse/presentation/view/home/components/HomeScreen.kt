package com.darkzoom.newspulse.presentation.view.home.components

/*
 * HomeScreen.kt
 *
 * News feed screen built on top of NewsCard (see NewsCard.kt). Same package,
 * same dependencies — nothing extra needed beyond what NewsCard.kt requires.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * The app's news feed: a top bar plus a scrollable list of [NewsCard]s.
 *
 * @param newsItems articles to display
 * @param isLoading shows a centered spinner instead of the list while true
 * @param onNewsClick called with the tapped article
 * @param modifier modifier applied to the root [Scaffold]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    newsItems: List<NewsItem>,
    isLoading: Boolean = false,
    onNewsClick: (NewsItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("News") } )

        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()

                newsItems.isEmpty() -> Text(
                    text = "No articles yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(newsItems, key = { it.title + it.date }) { news ->
                        NewsCard(news = news, onClick = { onNewsClick(news) })
                    }
                }
            }
        }
    }
}

// Sample data + preview, same pattern as NewsCard.kt.
private val sampleNews = listOf(
    NewsItem(
        imageUrl = "https://picsum.photos/800/400?random=1",
        title = "Tech Giants Unveil New AI Breakthrough at Annual Conference",
        description = "Industry leaders showcased advancements in artificial " +
                "intelligence that promise to reshape everyday technology.",
        date = "June 17, 2026"
    ),
    NewsItem(
        imageUrl = "https://picsum.photos/800/400?random=2",
        title = "Global Markets Rally on Easing Inflation Data",
        description = "Stocks climbed worldwide after fresh figures suggested " +
                "inflation is cooling faster than economists expected.",
        date = "June 16, 2026"
    ),
    NewsItem(
        imageUrl = "https://picsum.photos/800/400?random=3",
        title = "City Unveils Plans for New Riverside Park",
        description = "The proposal includes walking trails, a community " +
                "garden, and an outdoor amphitheater along the waterfront.",
        date = "June 15, 2026"
    )
)

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(newsItems = sampleNews)
    }
}