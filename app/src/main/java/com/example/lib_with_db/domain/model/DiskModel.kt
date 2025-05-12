package com.example.lib_with_db.domain.model


data class Disk(
    override val itemId: String = "0",
    override val itemName: String,
    val diskType: String,
    override val isAvailable: Boolean,
    override val createdAt: String = System.currentTimeMillis().toString()
) : Item(itemId, itemName, isAvailable,  createdAt){


    companion object {
        fun createEmptyDisk() = Disk(
            itemName = "",
            diskType = "",
            isAvailable = true,

        )
    }
}
