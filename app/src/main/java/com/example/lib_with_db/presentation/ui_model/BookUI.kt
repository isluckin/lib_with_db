package com.example.lib_with_db.presentation.ui_model

data class BookUI (
    override val itemId: String = "0",
    override val itemName: String?,
    val bookAuthor: String?,
    val bookPages: Int?,
    override val isAvailable: Boolean,
    override val imageRes: Int?,
    val imageUrl: String?,
    override val createdAt: String? = System.currentTimeMillis().toString()
): ItemUI(itemId, itemName, isAvailable, imageRes, createdAt)