package com.example.currencytracking.domain.formatters

import com.example.currencytracking.domain.models.RatesResponse
import com.example.currencytracking.data.viewobjects.RateVo
import com.example.currencytracking.data.viewobjects.RatesVo

class RatesFormatter {

    fun format(
        ratesResponse: RatesResponse,
        favoriteCurrencies: List<String>,
        isAlphabetSorting: Boolean,
        isAscendingOrder: Boolean,
    ): RatesVo {
        var listRates = ratesResponse.rates
            .toList()
            .map { currencyWithValue ->
                val currency = currencyWithValue.first
                val value = currencyWithValue.second
                val isFavorite = favoriteCurrencies.contains(currency)
                RateVo(
                    currency = currency,
                    value = value,
                    favorite = isFavorite,
                )
            }

        listRates = if (isAlphabetSorting) {
            sortByCurrency(listRates, isAscendingOrder)
        } else {
            sortByValue(listRates, isAscendingOrder)
        }

        return RatesVo(
            rates = listRates
        )
    }

    fun formatFavorites(
        ratesResponse: RatesResponse,
        isAlphabetSorting: Boolean,
        isAscendingOrder: Boolean,
    ): RatesVo {
        var listRates = ratesResponse.rates
            .toList()
            .map { currencyWithValue ->
                RateVo(
                    currency = currencyWithValue.first,
                    value = currencyWithValue.second,
                    favorite = true,
                )
            }

        listRates = if (isAlphabetSorting) {
            sortByCurrency(listRates, isAscendingOrder)
        } else {
            sortByValue(listRates, isAscendingOrder)
        }

        return RatesVo(
            rates = listRates
        )
    }

    private fun sortByCurrency(rates: List<RateVo>, isAscendingOrder: Boolean): List<RateVo> {
        val result = rates.sortedBy { it.currency }
        return order(result, isAscendingOrder)
    }

    private fun sortByValue(rates: List<RateVo>, isAscendingOrder: Boolean): List<RateVo> {
        val result = rates.sortedBy { it.value }
        return order(result, isAscendingOrder)
    }

    private fun order(rates: List<RateVo>, isAscendingOrder: Boolean): List<RateVo> {
        return if (isAscendingOrder) {
            rates
        } else {
            rates.reversed()
        }
    }

}