package com.example.currencytracking.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencytracking.data.dto.FavoriteCurrency

@Database(entities = [FavoriteCurrency::class], version = 1, exportSchema = false)
abstract class FavoriteCurrenciesDatabase : RoomDatabase() {

    abstract fun favoriteCurrenciesDao(): FavoriteCurrenciesDao

    companion object {

        @Volatile
        private var instance: FavoriteCurrenciesDatabase? = null

        fun getDatabase(context: Context): FavoriteCurrenciesDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(
                appContext,
                FavoriteCurrenciesDatabase::class.java,
                "favorite_currencies"
            )
                .fallbackToDestructiveMigration()
                .build()

    }

}