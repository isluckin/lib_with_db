package com.example.lib_with_db.domain.usecase

import androidx.compose.ui.geometry.Offset
import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.repository.ItemRepository
import com.example.lib_with_db.presentation.ui_model.SortType


class GetItemsUseCase(private val repository: ItemRepository) {
    suspend operator fun invoke(sortType: SortType, offset: Int, limit: Int): List<Item> {
        return when (sortType) {
            SortType.BY_NAME -> repository.getItemsSortedByName(offset, limit)
            SortType.BY_DATE -> repository.getItemsSortedByDate(offset, limit)
        }
    }


}