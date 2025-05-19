package com.example.di


import dagger.Provides
import kotlin.jvm.java

@Module
object DbModule {
    @Singleton
    @Provides
    fun provideAppDatabase(context: android.content.Context): AppDatabase{
        return androidx.room.Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "library.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideItemDao(database: AppDatabase): ItemDao = database.itemDao()
}
@Provides
fun provideSortPreferenceDao(database: AppDatabase): SortPreferenceDao = database.sortPreferenceDao()
