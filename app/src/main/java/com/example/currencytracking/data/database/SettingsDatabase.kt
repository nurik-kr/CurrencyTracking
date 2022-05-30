package com.example.currencytracking.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencytracking.data.dto.Setting

@Database(entities = [Setting::class], version = 1, exportSchema = false)
abstract class SettingsDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDao

    companion object {

        @Volatile
        private var instance: SettingsDatabase? = null

        fun getDatabase(context: Context): SettingsDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, SettingsDatabase::class.java, "settings")
                .fallbackToDestructiveMigration()
                .build()

    }

}