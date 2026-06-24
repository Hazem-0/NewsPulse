package com.darkzoom.newspulse.di

import com.darkzoom.newspulse.screens.favorites.viewmodel.FavoritesViewModel
import com.darkzoom.newspulse.screens.home.viewmodel.HomeViewModel
import org.koin.dsl.module

val uiModule = module {
    factory { HomeViewModel(get(), get(), get()) }
    factory { FavoritesViewModel(get(), get()) }
}