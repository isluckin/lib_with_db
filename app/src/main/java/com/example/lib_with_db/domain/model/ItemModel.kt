package com.example.lib_with_db.domain.model


sealed class Item(
    open val itemId: String,
    open val itemName: String?,
    open val isAvailable: Boolean,
    open val createdAt: String? = System.currentTimeMillis().toString()
)