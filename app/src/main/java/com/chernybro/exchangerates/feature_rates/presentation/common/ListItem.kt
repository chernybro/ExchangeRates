package com.chernybro.exchangerates.feature_rates.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chernybro.exchangerates.feature_rates.domain.models.Rate
import com.chernybro.exchangerates.R

@Composable
fun ListItem(
    rate: Rate,
    icon: ImageVector
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
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rate.code,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(start = 8.dp)
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
                    .padding(end = 8.dp)
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = rememberVectorPainter(image = icon),
                    contentDescription = stringResource(id = R.string.add_to_favourites)
                )
            }
        }
    }
}

@Preview
@Composable
fun test() {
    ListItem(Rate("EUR", "Rub", 3.42), Icons.Default.Star)
}