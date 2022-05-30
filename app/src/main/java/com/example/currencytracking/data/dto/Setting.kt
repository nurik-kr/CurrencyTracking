package com.example.currencytracking.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Setting(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val parameter: String,
    val value: Boolean,
)