package com.example.lib_with_db.presentation.ui_model

import com.example.lib_with_db.R
import com.example.lib_with_db.domain.model.Newspaper

data class NewspaperUI ( override val itemId: String = "0",
                    override val itemName: String,
                    val newspaperNumber: Int,
                    val month: String,
                    override val isAvailable: Boolean,
                    override val imageRes: Int?,
                    override val createdAt: String = System.currentTimeMillis().toString()
) : ItemUI(itemId, itemName, isAvailable, imageRes, createdAt)
{
    companion object {

        fun createEmptyNewspaper() = NewspaperUI(
            itemName = "",
            newspaperNumber = 0,
            month = "",
            isAvailable = true,
            imageRes = R.drawable.newspaper_image
        )
    }
}