package repository_impl.mapper

import com.example.lib_with_db.domain.model.SortPreference
import com.example.lib_with_db.presentation.view_model.ItemViewModel
import local.entity.SortPreferenceEntity

internal  fun SortPreference.toEntity(): SortPreferenceEntity = SortPreferenceEntity(
    sortType = when(this){
        SortPreference.BY_DATE -> "DATE"
        SortPreference.BY_NAME -> "NAME"
    }
)
internal fun SortPreferenceEntity.toDomain(): SortPreference = when(this.sortType){
    "NAME" -> SortPreference.BY_NAME
    else -> SortPreference.BY_DATE
}
