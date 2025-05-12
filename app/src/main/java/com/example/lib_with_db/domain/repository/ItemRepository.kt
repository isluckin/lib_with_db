package com.example.lib_with_db.domain.repository


import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.model.Disk
import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.model.Newspaper
import com.example.lib_with_db.domain.model.SortPreference
import com.example.lib_with_db.presentation.view_model.ItemViewModel

interface ItemRepository {
    suspend fun getSortPreference(): SortPreference
    suspend fun saveSortPreference(sortPreference: SortPreference)
    suspend fun saveItem(item : Item)
    suspend fun getAllItemsCount(): Int
    suspend fun getItemsSortedByName(offset: Int, limit: Int): List<Item>
    suspend fun getItemsSortedByDate(offset: Int, limit: Int): List<Item>
    suspend fun getBooksSortedByName(offset: Int, limit: Int): List<Book>
    suspend fun getBooksSortedByDate(offset: Int, limit: Int): List<Book>
    suspend fun getNewspapersSortedByName(offset: Int, limit: Int): List<Newspaper>
    suspend fun getNewspapersSortedByDate(offset: Int, limit: Int): List<Newspaper>
    suspend fun getDisksSortedByName(offset: Int, limit: Int): List<Disk>
    suspend fun getDisksSortedByDate(offset: Int, limit: Int): List<Disk>
    suspend fun getItemsWithLimit(offset: Int, limit: Int): List<Item>
    suspend fun getBooksWithLimit(offset: Int, limit: Int): List<Book>
    suspend fun getNewspapersWithLimit(offset: Int, limit: Int): List<Newspaper>
    suspend fun getDisksWithLimit(offset: Int, limit: Int): List<Disk>
    suspend fun isItemInDB(itemId: String): Boolean
}