package com.example.lib_with_db

internal interface BookRepository {
    suspend fun getBooks(query: String): List<Book>
}
