package repository_impl.mapper

import android.util.Log
import com.example.lib_with_db.common.IdGenerator.generateCustomId

import com.example.lib_with_db.data.remote.response.GoogleBooksResponse
import com.example.lib_with_db.domain.model.Book


fun GoogleBooksResponse.toBook(): List<Book> {

    return this.items.map { book ->
        try {
            Book(
                itemId = book.id ?: generateCustomId(),
                itemName = book.volumeInfo.title ?: "No Title",
                bookAuthor = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author",
                bookPages = book.volumeInfo.pageCount ?: 0,
                isAvailable = true,
                createdAt = book.volumeInfo.publishedDate ?: "----.--.--",
                imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:"),
                imageRes = null
            )
        } catch (e: Exception) {
            Log.d("!MAPPING ER!", "In mapping")
        } as Book
    }
}