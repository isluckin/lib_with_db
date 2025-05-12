package com.example.lib_with_db.presentation.ui_mapper

import com.example.lib_with_db.domain.model.SortPreference
import com.example.lib_with_db.presentation.ui_model.SortType

 object SortPrefMapper {
    internal fun SortType.toModel(): SortPreference =
        when (this) {
            SortType.BY_DATE -> SortPreference.BY_DATE
            SortType.BY_NAME -> SortPreference.BY_NAME
        }

    internal fun SortPreference.toUI(): SortType =
        when (this) {
            SortPreference.BY_NAME -> SortType.BY_NAME
            SortPreference.BY_DATE -> SortType.BY_DATE
        }
}