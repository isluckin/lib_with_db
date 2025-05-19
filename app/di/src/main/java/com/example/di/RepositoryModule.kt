package com.example.di

import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideItemRepository(
        itemDao: ItemDao,
        sortPreferenceDao: SortPreferenceDao
    ): ItemRepository {
        return ItemRepositoryImpl(itemDao, sortPreferenceDao)
    }

    @Singleton
    @Provides
    fun provideRemoteBookRepository(api: GoogleBooksAPI): BookRepository
    {
        return RemoteBooksRepository(api)
    }
}