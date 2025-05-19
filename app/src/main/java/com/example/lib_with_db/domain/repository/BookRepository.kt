package com.example.lib_with_db.domain.repository

import com.example.lib_with_db.domain.model.Book

 interface BookRepository {
    suspend fun getBooks(query: String): List<Book>
}
