package com.example.lib_with_db.presentation.ui_model

import com.example.lib_with_db.domain.model.Item

data class NewspaperUI ( override val itemId: String = "0",
                    override val itemName: String,
                    val newspaperNumber: Int,
                    val month: String,
                    override val isAvailable: Boolean,
                    override val imageRes: Int?,
                    override val createdAt: String = System.currentTimeMillis().toString()
) : ItemUI(itemId, itemName, isAvailable, imageRes, createdAt)