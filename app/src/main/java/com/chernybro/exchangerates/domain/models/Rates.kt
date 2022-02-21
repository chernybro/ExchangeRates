package com.chernybro.exchangerates.domain.models

import com.google.gson.annotations.SerializedName

data class Rates(

    @SerializedName("USD") val uSD : Double,
    @SerializedName("JPY") val jPY : Double,
    @SerializedName("BGN") val bGN : Double,
    @SerializedName("CZK") val cZK : Double,
    @SerializedName("DKK") val dKK : Double,
    @SerializedName("GBP") val gBP : Double,
    @SerializedName("HUF") val hUF : Double,
    @SerializedName("PLN") val pLN : Double,
    @SerializedName("RON") val rON : Double,
    @SerializedName("SEK") val sEK : Double,
    @SerializedName("CHF") val cHF : Double,
    @SerializedName("ISK") val iSK : Double,
    @SerializedName("NOK") val nOK : Double,
    @SerializedName("HRK") val hRK : Double,
    @SerializedName("RUB") val rUB : Double,
    @SerializedName("TRY") val tRY : Double,
    @SerializedName("AUD") val aUD : Double,
    @SerializedName("BRL") val bRL : Double,
    @SerializedName("CAD") val cAD : Double,
    @SerializedName("CNY") val cNY : Double,
    @SerializedName("HKD") val hKD : Double,
    @SerializedName("IDR") val iDR : Double,
    @SerializedName("ILS") val iLS : Double,
    @SerializedName("INR") val iNR : Double,
    @SerializedName("KRW") val kRW : Double,
    @SerializedName("MXN") val mXN : Double,
    @SerializedName("MYR") val mYR : Double,
    @SerializedName("NZD") val nZD : Double,
    @SerializedName("PHP") val pHP : Double,
    @SerializedName("SGD") val sGD : Double,
    @SerializedName("THB") val tHB : Double,
    @SerializedName("ZAR") val zAR : Double,
    @SerializedName("EUR") val eUR : Double
)
