package com.example.lib_with_db.presentation.ui_model

sealed  class  ItemUI(
    open val itemId: String,
    open val itemName: String?,
    open val isAvailable: Boolean,
    open val imageRes: Int?,
    open val createdAt: String? = System.currentTimeMillis().toString()
)