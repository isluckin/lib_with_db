package com.example.di

import dagger.Binds



@Module
abstract class  ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindItemViewModel(viewModel: ItemViewModel): ViewModel
}
