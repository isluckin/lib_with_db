package com.example.di

import com.android.manifmerger.ManifestSystemProperty.Application
import dagger.Provides

@Module
class AppModule {


    @Provides
    fun provideAddItemUseCase(repository: ItemRepository): AddItemUseCase {
        return AddItemUseCase(repository)
    }

    @Provides
    fun provideCheckItemExistsUseCase(repository: ItemRepository): CheckItemExistsUseCase {
        return CheckItemExistsUseCase(repository)
    }

    @Provides
    fun provideGetItemsUseCase(repository: ItemRepository): GetItemsUseCase {
        return GetItemsUseCase(repository)
    }

    @Provides
    fun provideLoadMoreItemsUseCase(repository: ItemRepository): LoadMoreItemsUseCase {
        return LoadMoreItemsUseCase(repository)
    }

    @Provides
    fun provideManageSortPreferenceUseCase(repository: ItemRepository): ManageSortPreferenceUseCase {
        return ManageSortPreferenceUseCase(repository)
    }

    @Provides
    fun provideSearchBooksUseCase(remoteRepository: BookRepository): SearchBooksUseCase {
        return SearchBooksUseCase(remoteRepository)
    }
}

    @Provides
    fun provideItemViewModel(
        getItemsUseCase: GetItemsUseCase,
        addItemUseCase: AddItemUseCase,
        loadMoreItemsUseCase: LoadMoreItemsUseCase,
        checkItemExistsUseCase: CheckItemExistsUseCase,
        manageSortPreference: ManageSortPreferenceUseCase,
        searchBooksUseCase: SearchBooksUseCase
    ): ItemViewModel {
        return ItemViewModel(
            getItemsUseCase,
            addItemUseCase,
            loadMoreItemsUseCase,
            checkItemExistsUseCase,
            manageSortPreference,
            searchBooksUseCase
        )
    }


