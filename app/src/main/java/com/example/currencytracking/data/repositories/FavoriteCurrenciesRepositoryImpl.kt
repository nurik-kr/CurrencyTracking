package com.example.currencytracking.data.repositories

import com.example.currencytracking.data.database.FavoriteCurrenciesDao
import com.example.currencytracking.data.dto.FavoriteCurrency
import com.example.currencytracking.domain.mappers.FavoriteCurrenciesMapper
import com.example.currencytracking.domain.repositories.FavoriteCurrenciesRepository
import javax.inject.Inject

class FavoriteCurrenciesRepositoryImpl @Inject constructor(
    private val dao: FavoriteCurrenciesDao,
    private val mapper: FavoriteCurrenciesMapper,
) : FavoriteCurrenciesRepository {

    override suspend fun getAllFavoriteCurrencies(): List<String> {
        return mapper.map(dao.getAllFavoriteCurrencies())
    }

    override suspend fun getByCurrency(currency: String): List<FavoriteCurrency> {
        return dao.getByCurrency(currency)
    }

    override suspend fun getCountFavoriteCurrencies(): Int {
        return dao.getCountFavoriteCurrencies()
    }

    override suspend fun insert(currency: FavoriteCurrency) {
        dao.insert(currency)
    }

    override suspend fun delete(currency: FavoriteCurrency) {
        dao.delete(currency)
    }

    override suspend fun deleteAllFavoriteCurrencies() {
        dao.deleteAllFavoriteCurrencies()
    }

}