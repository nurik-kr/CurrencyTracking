package com.example.currencytracking.data.remote

import com.example.currencytracking.data.constants.HttpRequests.Companion.API_KEY
import com.example.currencytracking.data.constants.HttpRequests.Companion.BASE_CURRENCY
import com.example.currencytracking.data.constants.HttpRequests.Companion.FIELD_API_KEY
import com.example.currencytracking.data.constants.HttpRequests.Companion.FIELD_SYMBOLS
import com.example.currencytracking.data.dto.RatesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("latest")
    suspend fun getAllCurrency(
        @Query(BASE_CURRENCY) base: String,
        @Query(FIELD_API_KEY) apiKey: String = API_KEY
    ): RatesResponseDto

    @GET("latest")
    suspend fun getRatesForSpecificCurrencies(
        @Query(FIELD_SYMBOLS) symbols: String,
        @Query(FIELD_API_KEY) accessKey: String = API_KEY
    ): RatesResponseDto
}