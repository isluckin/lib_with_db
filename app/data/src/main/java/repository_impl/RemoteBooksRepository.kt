package repository_impl

import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.repository.BookRepository
import remote.api.GoogleBooksAPI
import repository_impl.mapper.toBook


 class RemoteBooksRepository(
    private val api: GoogleBooksAPI
) : BookRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val response = api.searchBooks(
            query = query,
            maxResults = 20,
            apiKey = BuildConfig.GOOGLE_BOOKS_API_KEY
        )
        return response.toBook()
    }
}