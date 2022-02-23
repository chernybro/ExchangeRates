package com.chernybro.exchangerates.feature_rates.presentation.exchange_list

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chernybro.exchangerates.feature_rates.presentation.common.DefaultList


@Composable
fun RateListScreen(
    navController: NavController,
    viewModel: RatesViewModel = hiltViewModel()
) {
    DefaultList(navController = navController, viewModel = viewModel, iconItem = Icons.Outlined.Home)
}