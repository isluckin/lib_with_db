package com.example.lib_with_db


import android.os.Parcelable
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
sealed class Item(
    open val itemId: String,
    open val itemName: String?,
    open val isAvailable: Boolean,
    open val imageRes: Int,
    open val createdAt: String? = System.currentTimeMillis().toString()
) : Parcelable


@Parcelize
data class Book(
    override val itemId: String = "0",
    override val itemName: String?,
    val bookAuthor: String?,
    val bookPages: Int?,
    override val isAvailable: Boolean,
    override val imageRes: Int,
    val imageUrl: String?,
    override val createdAt: String? = System.currentTimeMillis().toString()
) : Item(itemId, itemName, isAvailable, imageRes, createdAt) {


    companion object {
        fun createEmptyBook() = Book(
            itemName = "",
            bookAuthor = "",
            bookPages = 0,
            imageRes = R.drawable.book_image,
            imageUrl = "",
            isAvailable = true
        )
    }
}

@Parcelize
data class Newspaper(
    override val itemId: String = "0",
    override val itemName: String,
    val newspaperNumber: Int,
    val month: String,
    override val isAvailable: Boolean,
    override val imageRes: Int,
    override val createdAt: String = System.currentTimeMillis().toString()
) : Item(itemId, itemName, isAvailable, imageRes, createdAt) {


    companion object {

        fun createEmptyNewspaper() = Newspaper(
            itemName = "",
            newspaperNumber = 0,
            month = "",
            isAvailable = true,
            imageRes = R.drawable.newspaper_image
        )
    }
}

@Parcelize
data class Disk(
    override val itemId: String = "0",
    override val itemName: String,
    val diskType: String,
    override val isAvailable: Boolean,
    override val imageRes: Int,
    override val createdAt: String = System.currentTimeMillis().toString()
) : Item(itemId, itemName, isAvailable, imageRes, createdAt) {


    companion object {
        fun createEmptyDisk() = Disk(
            itemName = "", diskType = "", isAvailable = true, imageRes = R.drawable.disk_image
        )
    }
}

@Entity(tableName = "base_items")
data class BaseItemEntity(
    @PrimaryKey val id: String,
    val type: String,
    val name: String?,
    val isAvailable: Boolean,
    val imageRes: Int,
    val createdAt: String?
)


@Entity(tableName = "book_details", primaryKeys = ["itemId"])
data class BookDetailsEntity(
    val itemId: String, val author: String?, val pageCount: Int?, val imageUrl: String?
)


@Entity(tableName = "newspaper_details", primaryKeys = ["itemId"])
data class NewspaperDetailsEntity(
    val itemId: String, val month: String, val newspaperNumber: Int
)


@Entity(tableName = "disk_details", primaryKeys = ["itemId"])
data class DiskDetailsEntity(
    val itemId: String, val diskType: String
)

@Serializable
data class GoogleBooksResponse(
    @SerialName("items") val items: List<BookItem> = emptyList()
)

@Serializable
data class BookItem(
    @SerialName("id") val id: String? = generateCustomId(),
    @SerialName("volumeInfo") val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    @SerialName("title") val title: String? = "No Title",
    @SerialName("authors") val authors: List<String>? = emptyList<String>(),
    @SerialName("pageCount") val pageCount: Int? = 0,
    @SerialName("publishedDate") val publishedDate: String? = "----.--.--",
    @SerialName("imageLinks") val imageLinks: ImageLinks? = null

)

@Serializable
data class ImageLinks(
    @SerialName("thumbnail") val thumbnail: String? = null
)

internal object BookResponseMapper {
    fun map(response: GoogleBooksResponse): List<Book> {

        return response.items.map { book ->
            try {
                Book(
                    itemId = book.id ?: generateCustomId(),
                    itemName = book.volumeInfo.title ?: "No Title",
                    bookAuthor = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author",
                    bookPages = book.volumeInfo.pageCount ?: 0,
                    isAvailable = true,
                    createdAt = book.volumeInfo.publishedDate ?: "----.--.--",
                    imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"),
                    imageRes = R.drawable.book_image
                )
            } catch (e: Exception) {
                Log.d("!MAPPING ER!", "In mapping")
            } as Book
        }
    }
}

fun generateCustomId(length: Int = 13): String {
    val chars = ('A'..'Z')
    val randomPart = (1..(length - 2)).map { chars.random() }.joinToString("")
    return "DF$randomPart"
}