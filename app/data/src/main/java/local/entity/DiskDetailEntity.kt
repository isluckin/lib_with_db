package local.entity

import androidx.room.Entity


@Entity(tableName = "disk_details", primaryKeys = ["itemId"])
data class DiskDetailsEntity(
    val itemId: String, val diskType: String
)