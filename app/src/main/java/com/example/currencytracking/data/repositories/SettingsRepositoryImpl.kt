package com.example.currencytracking.data.repositories

import com.example.currencytracking.data.database.SettingsDao
import com.example.currencytracking.data.dto.Setting
import com.example.currencytracking.domain.mappers.SettingsMapper
import com.example.currencytracking.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dao: SettingsDao,
    private val mapper: SettingsMapper,
) : SettingsRepository {

    override suspend fun getAllSettings(): Map<String, Boolean> {
        return mapper.map(dao.getAllSettings())
    }

    override suspend fun getByParameter(parameter: String): List<Setting> {
        return dao.getByParameter(parameter)
    }

    override suspend fun insert(setting: Setting) {
        dao.insert(setting)
    }

    override suspend fun update(setting: Setting) {
        dao.update(setting)
    }

}