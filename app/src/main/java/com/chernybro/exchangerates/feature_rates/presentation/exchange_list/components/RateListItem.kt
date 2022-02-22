package com.chernybro.exchangerates.feature_rates.presentation.exchange_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chernybro.exchangerates.feature_rates.domain.models.Rate

@Composable
fun RateListItem(
    rate: Rate
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(72.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = rate.code,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
        )
        Text(
            text = rate.cost.toString(),
            color = Color.Yellow,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 8.dp)
        )
    }
}