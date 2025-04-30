package com.example.lib_with_db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sort_preference")
data class SortPreference(
    @PrimaryKey val id: Int = 1,
    val sortType: String
)