package com.example.currencytracking.domain.repositories

import com.example.currencytracking.data.dto.FavoriteCurrency

interface FavoriteCurrenciesRepository {

    suspend fun getAllFavoriteCurrencies(): List<String>

    suspend fun getByCurrency(currency: String): List<FavoriteCurrency>

    suspend fun getCountFavoriteCurrencies(): Int

    suspend fun insert(currency: FavoriteCurrency)

    suspend fun delete(currency: FavoriteCurrency)

    suspend fun deleteAllFavoriteCurrencies()

}