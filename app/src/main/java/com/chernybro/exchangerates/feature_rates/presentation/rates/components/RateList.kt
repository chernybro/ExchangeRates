package com.chernybro.exchangerates.feature_rates.presentation.rates.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chernybro.exchangerates.R
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.feature_rates.presentation.BaseViewModel
import com.chernybro.exchangerates.feature_rates.presentation.common.Event
import com.chernybro.exchangerates.feature_rates.presentation.common.components.OrderSection
import com.chernybro.exchangerates.ui.theme.SpaceLarge
import com.chernybro.exchangerates.ui.theme.SpaceMedium
import com.chernybro.exchangerates.ui.theme.SpaceSmall
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun RateList(
    viewModel: BaseViewModel = hiltViewModel()
) {
    val state = viewModel.state.value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = SpaceMedium, horizontal = SpaceLarge)
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
                    viewModel.onEvent(Event.ToggleOrderSection)
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
                    .padding(vertical = SpaceMedium),
                rateOrder = state.rateOrder,
                onOrderChange = {
                    viewModel.onEvent(Event.Order(it))
                }
            )
        }
        Spacer(modifier = Modifier.height(SpaceLarge))
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = state.isRefresging),
            onRefresh = { viewModel.onEvent(Event.SelectRates(state.selectedSymbol)) }
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.rates) { rate ->
                    RatesItem(
                        rate = rate,
                        onRateChange =  {
                            if (!rate.isFavourite) {
                                viewModel.onEvent(Event.AddRate(rate.base, rate))
                            } else {
                                viewModel.onEvent(Event.DeleteRate(rate))
                            }
                        }
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
}

@Composable
fun DropDownList(viewModel: BaseViewModel) {

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
                modifier = Modifier.padding(SpaceSmall)
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
                            viewModel.onEvent(Event.SelectRates(symbol))
                        }) {
                        Text(text = symbol.code)
                    }
                }
            }
        }
    }
}


@Composable
fun RatesItem(
    rate: Rate,
    onRateChange: () -> Unit
) {
    val icon = if (rate.isFavourite) {
        Icons.Filled.Star
    } else {
        ImageVector.vectorResource(id = R.drawable.ic_baseline_star_outline_24)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(vertical = SpaceSmall, horizontal = SpaceMedium)
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rate.code,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(start = SpaceMedium)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = rate.cost.toString(),
                color = Color.Yellow,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(end = SpaceMedium)
            )
            IconButton(onClick = {
                onRateChange.invoke()
            }) {
                Icon(
                    painter = rememberVectorPainter(image = icon),
                    contentDescription = stringResource(id = R.string.add_to_favourites),
                    tint = Color.Green
                )
            }
        }
    }
}