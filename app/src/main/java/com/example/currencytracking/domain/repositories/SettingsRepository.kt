package com.example.currencytracking.domain.repositories

import com.example.currencytracking.data.dto.Setting

interface SettingsRepository {

    suspend fun getAllSettings(): Map<String, Boolean>

    suspend fun getByParameter(parameter: String): List<Setting>

    suspend fun insert(setting: Setting)

    suspend fun update(setting: Setting)

}