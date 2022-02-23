package com.chernybro.exchangerates.feature_rates.presentation.exchange_favourites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chernybro.exchangerates.feature_rates.presentation.common.DefaultList
import com.chernybro.exchangerates.feature_rates.presentation.exchange_list.RatesViewModel


@Composable
fun FavouritesListScreen(
    navController: NavController,
    viewModel: RatesViewModel = hiltViewModel()
) {
    // TODO: add action on icon click,
    DefaultList(navController = navController, viewModel = viewModel)
}