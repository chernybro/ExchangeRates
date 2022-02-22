package com.chernybro.exchangerates.feature_rates.presentation.exchange_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chernybro.exchangerates.feature_rates.domain.utils.OrderType
import com.chernybro.exchangerates.feature_rates.domain.utils.RateOrder
import com.chernybro.exchangerates.R

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    rateOrder: RateOrder = RateOrder.Code(OrderType.Ascending),
    onOrderChange: (RateOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_code),
                selected = rateOrder is RateOrder.Code,
                onSelect = { onOrderChange(RateOrder.Code(rateOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_cost),
                selected = rateOrder is RateOrder.Cost,
                onSelect = { onOrderChange(RateOrder.Cost(rateOrder.orderType)) }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = rateOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(rateOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = rateOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(rateOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}