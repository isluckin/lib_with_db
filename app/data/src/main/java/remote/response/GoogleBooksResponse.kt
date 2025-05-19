package remote.response


import com.example.common.IdGenerator
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GoogleBooksResponse(
    @SerialName("items") val items: List<BookItem> = emptyList()
)

@Serializable
data class BookItem(
    @SerialName("id") val id: String? = IdGenerator.generateCustomId(),
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