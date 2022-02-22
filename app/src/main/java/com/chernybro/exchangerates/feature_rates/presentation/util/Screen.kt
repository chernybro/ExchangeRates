package com.chernybro.exchangerates.feature_rates.presentation.util


sealed class Screen(val route: String) {
    object RateListScreen : Screen("rates_screen")
    object FavouritesScreen : Screen("favourites_screen")
}
