package com.chernybro.exchangerates.feature_rates.presentation.exchange_list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol


@Composable
fun DropDownList(symbols: List<Symbol>) {

    // State variables
    var expanded by remember { mutableStateOf(false)} // TODO: add to ListState

    Box(contentAlignment = Alignment.Center) {
        Row(
            Modifier
                .padding(start = 32.dp)
                .clickable {
                    expanded = !expanded
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) { // Anchor view
            Text(text = countryName,fontSize = 18.sp,modifier = Modifier.padding(end = 8.dp)) // Country name label
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")

            //
            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            }) {
                countriesList.forEach{ country->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        countryName = country
                    }) {
                        Text(text = country)
                    }
                }
            }
        }
    }

}