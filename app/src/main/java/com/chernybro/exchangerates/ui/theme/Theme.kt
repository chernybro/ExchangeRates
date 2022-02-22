package com.chernybro.exchangerates.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.chernybro.exchangerates.presentation.ui.theme.*

private val DarkColorPalette = darkColors(
    primary = Yellow,
    background = DarkGray,
    surface = StandardGray
)

@Composable
fun ExchangeRatesTheme(
    content: @Composable () -> Unit
) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}