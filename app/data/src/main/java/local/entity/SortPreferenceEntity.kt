package local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sort_preference")
data class SortPreferenceEntity(
    @PrimaryKey val id: Int = 1,
    val sortType: String
)