package com.example.lib_with_db.domain.usecase

import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.repository.ItemRepository

class AddItemUseCase (
    private val repository: ItemRepository
) {
    suspend operator fun invoke(item: Item) {
        repository.saveItem(item)
    }
}