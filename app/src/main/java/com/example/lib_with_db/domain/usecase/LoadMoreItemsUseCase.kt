package com.example.lib_with_db.domain.usecase

import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.repository.ItemRepository

class LoadMoreItemsUseCase(private val repository: ItemRepository) {
    data class PaginationResult(
        val newItems: List<Item>,
        val updatedOffset: Int,
        val itemsToRemove: Int,
        val shouldClearFromStart: Boolean
    )

    suspend operator fun invoke(
        currentOffset: Int,
        isForwardPagination: Boolean,
        pageSize: Int,
        initialLoadCount: Int,
        currentItemsCount: Int
    ): PaginationResult? {
        return try {
            if (isForwardPagination) {
                val newItems = repository.getItemsWithLimit(currentOffset, pageSize)
                if (newItems.isEmpty()) return null

                val itemsToRemove = if (currentItemsCount + newItems.size > initialLoadCount) {
                    minOf(pageSize, currentItemsCount + newItems.size - initialLoadCount)
                } else {
                    0
                }

                PaginationResult(
                    newItems = newItems,
                    updatedOffset = currentOffset + newItems.size,
                    itemsToRemove = itemsToRemove,
                    shouldClearFromStart = true
                )
            } else {
                val newOffset = maxOf(0, currentOffset - initialLoadCount - pageSize)
                val loadSize = minOf(pageSize, currentOffset - newOffset)

                if (loadSize <= 0) return null

                val newItems = repository.getItemsWithLimit(newOffset, loadSize)
                if (newItems.isEmpty()) return null

                val itemsToRemove = if (currentItemsCount + newItems.size > initialLoadCount) {
                    minOf(pageSize, currentItemsCount + newItems.size - initialLoadCount)
                } else {
                    0
                }

                PaginationResult(
                    newItems = newItems,
                    updatedOffset = newOffset,
                    itemsToRemove = itemsToRemove,
                    shouldClearFromStart = false
                )
            }
        } catch (e: Exception) {
            throw Exception("Failed to load items: ${e.message}")
        }
    }
}