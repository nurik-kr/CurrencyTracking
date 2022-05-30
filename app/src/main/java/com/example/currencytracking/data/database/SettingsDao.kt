package com.example.currencytracking.data.database

import androidx.room.*
import com.example.currencytracking.data.dto.Setting

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings")
    suspend fun getAllSettings(): List<Setting>

    @Query("SELECT * FROM settings WHERE parameter = :parameter")
    suspend fun getByParameter(parameter: String): List<Setting>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: Setting)

    @Update
    suspend fun update(setting: Setting)

}