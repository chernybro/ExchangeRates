package com.chernybro.exchangerates.feature_rates.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chernybro.exchangerates.feature_rates.presentation.exchange_list.RateListScreen
import com.chernybro.exchangerates.ui.theme.ExchangeRatesTheme
import com.chernybro.exchangerates.feature_rates.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRatesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RateListScreen.route
                    ) {
                        composable(
                            route = Screen.RateListScreen.route
                        ) {
                            RateListScreen(navController)
                        }
                       /* composable(
                            route = Screen.CoinDetailScreen.route + "/{coinId}"
                        ) {
                            CoinDetailScreen()
                        }*/
                    }
                }
            }
        }
    }
}