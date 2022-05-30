package com.example.currencytracking.data.repositories

import com.example.currencytracking.data.remote.CurrencyService
import com.example.currencytracking.domain.mappers.RatesMapper
import com.example.currencytracking.domain.models.RatesResponse
import com.example.currencytracking.domain.models.Result
import com.example.currencytracking.domain.repositories.RatesRepository
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val apiService: CurrencyService,
    private val mapper: RatesMapper
) : RatesRepository {

    override suspend fun getRates(base: String?): Result<RatesResponse> {
        return Result {
            mapper.map(base?.let { apiService.getAllCurrency(it) })
        }
    }

    override suspend fun getRatesForSpecificCurrencies(symbols: String): Result<RatesResponse> {
        return Result {
            mapper.map(apiService.getRatesForSpecificCurrencies(symbols = symbols))
        }
    }

}