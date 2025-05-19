package com.example.lib_with_db.domain.usecase

import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.repository.ItemRepository
import com.example.lib_with_db.presentation.ui_mapper.UIMapper.toModel
import com.example.lib_with_db.presentation.ui_model.ItemUI

class AddItemUseCase (
    private val repository: ItemRepository
) {
    suspend operator fun invoke(item: ItemUI) {
        repository.saveItem(item.toModel())
    }
}