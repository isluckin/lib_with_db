package com.example.lib_with_db.domain.usecase

import com.example.lib_with_db.data.repository_impl.RemoteBooksRepository
import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.repository.BookRepository

class SearchBooksUseCase (
    private val remoteRepository: BookRepository
) {
    suspend operator fun invoke(query: String): List<Book> {
        return remoteRepository.getBooks(query)
    }
}