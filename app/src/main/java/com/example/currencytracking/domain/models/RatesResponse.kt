package com.example.currencytracking.domain.models

data class RatesResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
) {
    companion object {

        val SUCCESSFUL = RatesResponse(
            success = true,
            timestamp = 1600000000L,
            base = "USD",
            date = "2022-05-29",
            hashMapOf()
        )

        val UNSUCCESSFUL = SUCCESSFUL.copy(
            success = false,
        )

    }
}