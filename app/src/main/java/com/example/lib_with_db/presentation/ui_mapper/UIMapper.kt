package com.example.lib_with_db.presentation.ui_mapper

import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.model.Disk
import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.model.Newspaper
import com.example.lib_with_db.presentation.ui_model.BookUI
import com.example.lib_with_db.presentation.ui_model.DiskUI
import com.example.lib_with_db.presentation.ui_model.ItemUI
import com.example.lib_with_db.presentation.ui_model.NewspaperUI
import com.example.lib_with_db.R

 object UIMapper {
    internal fun Item.toUI(): ItemUI = when (this) {
        is Book -> BookUI(
            itemName = itemName,
            isAvailable = isAvailable,
            itemId = itemId,
            imageUrl = imageUrl,
            bookAuthor = bookAuthor,
            bookPages = bookPages,
            imageRes = R.drawable.book_image
        )

        is Disk -> DiskUI(
            itemName = itemName,
            isAvailable = isAvailable,
            itemId = itemId,
            diskType = diskType,
            imageRes = R.drawable.disk_image
        )

        is Newspaper -> NewspaperUI(
            itemName = itemName,
            isAvailable = isAvailable,
            itemId = itemId,
            newspaperNumber = newspaperNumber,
            month = month,
            imageRes = R.drawable.newspaper_image
        )
    }

    internal fun ItemUI.toModel(): Item = when (this) {
        is BookUI -> Book(
            itemName = itemName,
            isAvailable = isAvailable,
            itemId = itemId,
            imageUrl = imageUrl,
            bookAuthor = bookAuthor,
            bookPages = bookPages
        )

        is NewspaperUI -> Newspaper(
            itemName = itemName, isAvailable = isAvailable,
            itemId = itemId,
            newspaperNumber = newspaperNumber,
            month = month
        )

        is DiskUI -> Disk(
            itemName = itemName, isAvailable = isAvailable,
            itemId = itemId,
            diskType = diskType
        )
    }
}