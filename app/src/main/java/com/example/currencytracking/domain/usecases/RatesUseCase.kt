package com.example.currencytracking.domain.usecases

import com.example.currencytracking.domain.models.RatesResponse
import com.example.currencytracking.domain.models.Result
import com.example.currencytracking.domain.repositories.RatesRepository
import javax.inject.Inject

class RatesUseCase @Inject constructor(
    private val repository: RatesRepository
) {

    suspend fun getRates(base: String?): com.example.currencytracking.domain.models.Result<RatesResponse> {
        return repository.getRates(base)
    }

    suspend fun getRatesForSpecificCurrencies(specificCurrencies: List<String>): Result<RatesResponse> {
        val symbols = specificCurrencies.joinToString(separator = ",")
        return repository.getRatesForSpecificCurrencies(symbols)
    }

}