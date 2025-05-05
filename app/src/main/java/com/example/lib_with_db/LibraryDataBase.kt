package com.example.lib_with_db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BaseItemEntity::class, BookDetailsEntity::class, NewspaperDetailsEntity::class, DiskDetailsEntity::class, SortPreference::class],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun sortPreferenceDao(): SortPreferenceDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "library.db"
            ).fallbackToDestructiveMigration().build()

        }
    }
}