package com.example.currencytracking.domain.mappers

import com.example.currencytracking.data.dto.RatesResponseDto
import com.example.currencytracking.data.dto.RatesResponseDto.Companion.UNSUCCESSFUL
import com.example.currencytracking.domain.models.RatesResponse

class RatesMapper {

    fun map(ratesResponseDto: RatesResponseDto?): RatesResponse {
        val value = ratesResponseDto ?: UNSUCCESSFUL

        val success = value.success ?: false
        val timestamp = value.timestamp ?: 0L
        val base = value.base ?: ""
        val date = value.date ?: ""
        val rates = value.rates ?: hashMapOf()

        return RatesResponse(
            success = success,
            timestamp = timestamp,
            base = base,
            date = date,
            rates = rates
        )
    }

}