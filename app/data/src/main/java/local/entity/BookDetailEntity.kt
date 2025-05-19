package local.entity

import androidx.room.Entity

@Entity(tableName = "book_details", primaryKeys = ["itemId"])
data class BookDetailsEntity(
    val itemId: String, val author: String?, val pageCount: Int?, val imageUrl: String?
)
