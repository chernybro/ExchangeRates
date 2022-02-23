package com.chernybro.exchangerates.feature_rates.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chernybro.exchangerates.feature_rates.presentation.favourites.FavouritesListScreen
import com.chernybro.exchangerates.feature_rates.presentation.rates.RateListScreen
import com.chernybro.exchangerates.feature_rates.presentation.util.MainScreens
import com.chernybro.exchangerates.ui.theme.ExchangeRatesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangeRatesTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) }
                    ) {
                        AppNavigation(navController = navController)
                    }
                }
            }
        }
    }
}
@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        MainScreens.RateListScreen,
        MainScreens.FavouritesScreen
    )

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = stringResource(id = item.label)
                    )
                },
                label = { Text(text = stringResource(id = item.label)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.RateListScreen.route
    ) {
        composable(
            route = MainScreens.RateListScreen.route
        ) {
            RateListScreen()
        }
         composable(
             route = MainScreens.FavouritesScreen.route
         ) {
             FavouritesListScreen()
         }
    }
}