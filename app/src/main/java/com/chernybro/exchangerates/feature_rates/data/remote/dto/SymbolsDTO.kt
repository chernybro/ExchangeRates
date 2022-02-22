package com.chernybro.exchangerates.feature_rates.data.remote.dto

import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import com.google.gson.annotations.SerializedName

data class SymbolsDTO(
    @SerializedName("symbols") val symbols : Map<String, Any>
)

fun String.toSymbol() : Symbol {
    return Symbol(
        code = this
    )
}

fun SymbolsDTO.toSymbolsList() : List<Symbol> {
    return this.symbols.keys.map { string -> string.toSymbol() }.toList()
}

