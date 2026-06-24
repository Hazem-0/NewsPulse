@file:OptIn(KoinExperimentalAPI::class)

package com.darkzoom.newspulse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import org.koin.compose.viewmodel.koinViewModel
import com.darkzoom.newspulse.screens.home.viewmodel.HomeViewModel
import com.darkzoom.newspulse.screens.favorites.viewmodel.FavoritesViewModel
import com.darkzoom.newspulse.screens.home.view.HomeScreen
import com.darkzoom.newspulse.screens.favorites.view.FavoritesScreen
import org.koin.core.annotation.KoinExperimentalAPI


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Default.Home)
    data object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
}


@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    val screens = listOf(Screen.Home, Screen.Favorites)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    MaterialTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection  ),
            topBar = {
                CenterAlignedTopAppBar(

                    title = { Text("NewsPulse", fontWeight = FontWeight.Bold) },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                NavigationBar {
                    screens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentScreen == screen,
                            onClick = { currentScreen = screen }
                        )
                    }
                }
            }
        ) { innerPadding ->



            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    is Screen.Home -> {
                        val homeViewModel = koinViewModel<HomeViewModel>()
                        HomeScreen(viewModel = homeViewModel)
                    }
                    is Screen.Favorites -> {
                        val favoritesViewModel = koinViewModel<FavoritesViewModel>()
                        FavoritesScreen(viewModel = favoritesViewModel)
                    }
                }
            }
        }
    }
}