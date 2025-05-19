package local.entity

import androidx.room.Entity

@Entity(tableName = "newspaper_details", primaryKeys = ["itemId"])
data class NewspaperDetailsEntity(
    val itemId: String, val month: String, val newspaperNumber: Int
)
