package com.example.lib_with_db.domain.usecase

import com.example.lib_with_db.domain.repository.ItemRepository
import com.example.lib_with_db.presentation.ui_mapper.SortPrefMapper.toModel
import com.example.lib_with_db.presentation.ui_mapper.SortPrefMapper.toUI
import com.example.lib_with_db.presentation.ui_model.SortType

class ManageSortPreferenceUseCase(
    private val repository: ItemRepository
) {
    suspend fun getSortPreference(): SortType {
        return repository.getSortPreference().toUI()
    }

    suspend fun saveSortPreference(sortType: SortType) {
        repository.saveSortPreference(sortType.toModel())
    }
}