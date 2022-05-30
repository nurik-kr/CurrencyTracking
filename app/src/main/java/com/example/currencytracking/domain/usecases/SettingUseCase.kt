package com.example.currencytracking.domain.usecases

import com.example.currencytracking.domain.repositories.SettingsRepository
import com.example.currencytracking.data.dto.Setting
import javax.inject.Inject

class SettingUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {

    suspend fun getSettings(): Map<String, Boolean> {
        return repository.getAllSettings()
    }

    suspend fun getByParameter(parameter: String): List<Setting> {
        return repository.getByParameter(parameter)
    }

    suspend fun insert(setting: Setting) {
        repository.insert(setting)
    }

    suspend fun update(setting: Setting) {
        repository.update(setting)
    }

}