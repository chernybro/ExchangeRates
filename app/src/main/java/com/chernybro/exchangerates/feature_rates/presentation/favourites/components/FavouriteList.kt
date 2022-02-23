package com.chernybro.exchangerates.feature_rates.presentation.favourites.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chernybro.exchangerates.R
import com.chernybro.exchangerates.feature_rates.presentation.common.components.OrderSection
import com.chernybro.exchangerates.feature_rates.presentation.common.RatesEvent
import com.chernybro.exchangerates.feature_rates.presentation.favourites.FavouritesViewModel


@Composable
fun FavouriteList(
    viewModel: FavouritesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_title),
                style = MaterialTheme.typography.h5
            )
            DropDownList(viewModel)
            IconButton(
                onClick = {
                    viewModel.onEvent(RatesEvent.ToggleOrderSection)
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_sort_24),
                    contentDescription = stringResource(id = R.string.sort),
                    tint = Color.White
                )
            }
        }
        AnimatedVisibility(
            visible = state.isOrderSectionVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            OrderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                rateOrder = state.rateOrder,
                onOrderChange = {
                    viewModel.onEvent(RatesEvent.Order(it))
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.favourites) { rate ->
                com.chernybro.exchangerates.feature_rates.presentation.common.components.ListItem(
                    rate = rate,
                    viewModel = viewModel
                )
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun DropDownList(viewModel: FavouritesViewModel) {

    val symbols = viewModel.state.value.symbols
    val selectedSymbol = viewModel.state.value.selectedSymbol
    var expanded by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        Row(
            Modifier
                .padding(start = 32.dp)
                .clickable {
                    expanded = !expanded
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedSymbol.code,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 4.dp)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = ""
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                symbols.forEach{ symbol ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            viewModel.onEvent(RatesEvent.Select(symbol))
                        }) {
                        Text(text = symbol.code)
                    }
                }
            }
        }
    }

}