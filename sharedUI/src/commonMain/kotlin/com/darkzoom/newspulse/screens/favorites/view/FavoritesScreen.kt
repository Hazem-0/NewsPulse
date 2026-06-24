package com.darkzoom.newspulse.screens.favorites.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkzoom.newspulse.screens.favorites.viewmodel.FavoritesViewModel
import com.darkzoom.newspulse.screens.favorites.viewmodel.FavoritesUIEffect
import com.darkzoom.newspulse.screens.favorites.viewmodel.FavoritesUIEvent
import com.darkzoom.newspulse.screens.favorites.viewmodel.FavoritesUIState
import com.darkzoom.newspulse.screens.home.view.components.NewsCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is FavoritesUIEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(message = effect.message)
                }
            }
        }
    }

    FavoritesScreenContent(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
    uiState: FavoritesUIState,
    snackbarHostState: SnackbarHostState,
    onEvent: (FavoritesUIEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is FavoritesUIState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 4.dp
                )
            }
            is FavoritesUIState.Error -> {
                Text(
                    text = uiState.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is FavoritesUIState.Success -> {
                if (uiState.news.isEmpty()) {
                    Text(
                        text = "No favorite articles yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.news, key = { it.url }) { news ->
                            var isVisible by remember { mutableStateOf(true) }

                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = { dismissValue ->
                                    if (dismissValue == SwipeToDismissBoxValue.EndToStart ||
                                        dismissValue == SwipeToDismissBoxValue.StartToEnd
                                    ) {
                                        isVisible = false
                                        true
                                    } else {
                                        false
                                    }
                                }
                            )

                            LaunchedEffect(isVisible) {
                                if (!isVisible) {
                                    onEvent(FavoritesUIEvent.RemoveFavorite(news.url))
                                }
                            }

                            AnimatedVisibility(
                                visible = isVisible,
                                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                            ) {
                                SwipeToDismissBox(
                                    state = dismissState,
                                    backgroundContent = {
                                        val alignment = when (dismissState.dismissDirection) {
                                            SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                            SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                                            else -> Alignment.Center
                                        }
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    color = MaterialTheme.colorScheme.errorContainer,
                                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)
                                                )
                                                .padding(horizontal = 24.dp),
                                            contentAlignment = alignment
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.onErrorContainer
                                            )
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    NewsCard(
                                        news = news,
                                        onClick = { }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}