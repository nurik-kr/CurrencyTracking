package com.example.currencytracking.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_currencies")
data class FavoriteCurrency(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val currency: String,
)