package com.chernybro.exchangerates.feature_rates.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import com.chernybro.exchangerates.R


sealed class MainScreens(val route: String, val label: Int, val icon: ImageVector) {

    //Bottom Nav
    object RateListScreen : MainScreens(
        "rates_screen",
        R.string.bottom_nav_popular,
        Icons.Outlined.Home
    )
    object FavouritesScreen : MainScreens(
        "explore",
        R.string.bottom_nav_favourites,
        Icons.Outlined.Star
    )
}
