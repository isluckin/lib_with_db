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
import javax.inject.Inject

class ItemViewModelFactory @Inject constructor(
    private val itemViewModel: ItemViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ItemViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST") itemViewModel as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
