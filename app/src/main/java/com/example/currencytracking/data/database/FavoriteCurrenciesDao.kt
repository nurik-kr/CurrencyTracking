package com.example.currencytracking.data.database

import androidx.room.*
import com.example.currencytracking.data.dto.FavoriteCurrency

@Dao
interface FavoriteCurrenciesDao {

    @Query("SELECT * FROM favorite_currencies")
    suspend fun getAllFavoriteCurrencies(): List<FavoriteCurrency>

    @Query("SELECT * FROM favorite_currencies WHERE currency = :currency")
    suspend fun getByCurrency(currency: String): List<FavoriteCurrency>

    @Query("SELECT COUNT(*) FROM favorite_currencies")
    suspend fun getCountFavoriteCurrencies(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: FavoriteCurrency)

    @Delete
    suspend fun delete(currency: FavoriteCurrency)

    @Query("DELETE FROM favorite_currencies")
    suspend fun deleteAllFavoriteCurrencies()

}