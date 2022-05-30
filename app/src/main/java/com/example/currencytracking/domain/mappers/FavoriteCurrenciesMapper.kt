package com.example.currencytracking.domain.mappers

import com.example.currencytracking.data.dto.FavoriteCurrency

class FavoriteCurrenciesMapper {

    fun map(favoriteCurrencies: List<FavoriteCurrency>): List<String> {
        return favoriteCurrencies.map {
            it.currency
        }
    }

}