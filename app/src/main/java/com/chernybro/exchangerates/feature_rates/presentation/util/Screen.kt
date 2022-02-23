package com.chernybro.exchangerates.feature_rates.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.chernybro.exchangerates.R


sealed class Screen(val route: String) {
    object RateListScreen : Screen("rates_screen")
    object FavouritesScreen : Screen("favourites_screen")
}


sealed class IconScreens(val route: String, val label: Int, val icon: ImageVector) {

    //Bottom Nav
    object RateListScreen : IconScreens("rates_screen", R.string.bottom_nav_popular, Icons.Outlined.Home)
    object FavouritesScreen : IconScreens("explore", R.string.bottom_nav_favourites, Icons.Outlined.Star)
}
