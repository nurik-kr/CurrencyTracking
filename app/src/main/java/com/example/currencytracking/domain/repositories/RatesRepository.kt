package com.example.currencytracking.domain.repositories

import com.example.currencytracking.domain.models.RatesResponse
import com.example.currencytracking.domain.models.Result

interface RatesRepository {

    suspend fun getRates(base: String?): Result<RatesResponse>

    suspend fun getRatesForSpecificCurrencies(symbols: String): Result<RatesResponse>

}