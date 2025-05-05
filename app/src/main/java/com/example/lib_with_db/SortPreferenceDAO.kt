package com.example.lib_with_db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SortPreferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preference: SortPreference)

    @Query("SELECT * FROM sort_preference LIMIT 1")
    suspend fun getPreference(): SortPreference?
}