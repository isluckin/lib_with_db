package com.example.lib_with_db.domain.model


data class Book(
    override val itemId: String = "0",
    override val itemName: String?,
    val bookAuthor: String?,
    val bookPages: Int?,
    override val isAvailable: Boolean,
    val imageUrl: String?,
    override val createdAt: String? = System.currentTimeMillis().toString()
) : Item(itemId, itemName, isAvailable, createdAt)
{


    companion object {
        fun createEmptyBook() = Book(
            itemName = "",
            bookAuthor = "",
            bookPages = 0,
            imageUrl = "",
            isAvailable = true
        )
    }
}