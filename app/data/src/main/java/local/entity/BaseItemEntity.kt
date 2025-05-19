package local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "base_items")
data class BaseItemEntity(
    @PrimaryKey val id: String,
    val type: String,
    val name: String?,
    val isAvailable: Boolean,
    val createdAt: String?
)
