package com.example.currencytracking.domain.mappers

import com.example.currencytracking.data.dto.Setting

class SettingsMapper {

    fun map(settings: List<Setting>): Map<String, Boolean> {
        return mutableMapOf<String, Boolean>().let {
            settings.forEach { setting ->
                it[setting.parameter] = setting.value
            }
            it
        }
    }

}