package com.chernybro.exchangerates.feature_rates.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chernybro.exchangerates.feature_rates.presentation.exchange_list.RatesViewModel


@Composable
fun DropDownList(viewModel: RatesViewModel) {
    val state = viewModel.state.value

    val symbols = state.symbols
    val selectedSymbol = state.selectedSymbol
    var expanded by remember { mutableStateOf(false)}

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