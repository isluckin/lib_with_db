package com.example.lib_with_db.presentation.view_model


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lib_with_db.domain.usecase.AddItemUseCase
import com.example.lib_with_db.domain.usecase.CheckItemExistsUseCase
import com.example.lib_with_db.domain.usecase.GetItemsUseCase
import com.example.lib_with_db.domain.usecase.LoadMoreItemsUseCase
import com.example.lib_with_db.domain.usecase.ManageSortPreferenceUseCase
import com.example.lib_with_db.domain.usecase.SearchBooksUseCase

class ItemViewModelFactory(private val context: Context,
                           private val getItemsUseCase: GetItemsUseCase,
                           private val addItemUseCase: AddItemUseCase,
                           private val loadMoreItemsUseCase: LoadMoreItemsUseCase,
                           private val checkItemExistsUseCase: CheckItemExistsUseCase,
                           private val manageSortPreference: ManageSortPreferenceUseCase,
                           private val searchBooksUseCase: SearchBooksUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ItemViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST") ItemViewModel(context.applicationContext, getItemsUseCase, addItemUseCase, loadMoreItemsUseCase, checkItemExistsUseCase, manageSortPreference,searchBooksUseCase ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
