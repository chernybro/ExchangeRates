package com.chernybro.exchangerates.feature_rates.presentation.exchange_list

import android.graphics.drawable.VectorDrawable
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chernybro.exchangerates.R
import com.chernybro.exchangerates.feature_rates.presentation.components.RatesEvent
import com.chernybro.exchangerates.feature_rates.presentation.exchange_list.components.OrderSection


@Composable
fun RateListScreen(
    navController: NavController,
    viewModel: RateListViewModel = hiltViewModel()
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
                text = "Your note",
                style = MaterialTheme.typography.h4
            )
            IconButton(
                onClick = {
                    viewModel.onEvent(RatesEvent.ToggleOrderSection)
                },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_sort_24) ,
                    contentDescription = "Sort"
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
                    .padding(vertical = 16.dp),
                rateOrder = state.rateOrder,
                onOrderChange = {
                    viewModel.onEvent(RatesEvent.Order(it))
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.rates) { rate ->
                RateListItem(
                    rate = rate,
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}