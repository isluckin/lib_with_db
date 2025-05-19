package com.example.lib_with_db.domain.usecase

import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.repository.BookRepository
import com.example.lib_with_db.presentation.ui_mapper.UIMapper.toUI
import com.example.lib_with_db.presentation.ui_model.BookUI

class SearchBooksUseCase (
    private val remoteRepository: BookRepository
) {
    suspend operator fun invoke(query: String): List<BookUI> {
        return remoteRepository.getBooks(query).map { item -> item.toUI() as BookUI }
    }
}