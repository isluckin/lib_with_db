package com.example.lib_with_db

import androidx.room.util.query

internal class RemoteBooksRepository(
    private val api: GoogleBooksAPI
) : BookRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val response = api.searchBooks(
            query = query,
            maxResults = 20,
            apiKey = BuildConfig.GOOGLE_BOOKS_API_KEY
        )
        return BookResponseMapper.map(response)
    }
}