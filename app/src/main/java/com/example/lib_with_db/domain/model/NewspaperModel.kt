package com.example.lib_with_db.domain.model


data class Newspaper(
    override val itemId: String = "0",
    override val itemName: String,
    val newspaperNumber: Int,
    val month: String,
    override val isAvailable: Boolean,
    override val createdAt: String = System.currentTimeMillis().toString()
) : Item(itemId, itemName, isAvailable, createdAt) {


    companion object {

        fun createEmptyNewspaper() = Newspaper(
            itemName = "",
            newspaperNumber = 0,
            month = "",
            isAvailable = true,
        )
    }
}