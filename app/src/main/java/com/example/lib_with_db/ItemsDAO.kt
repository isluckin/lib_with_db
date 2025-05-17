package com.example.lib_with_db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBaseItem(item: BaseItemEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookDetails(details: BookDetailsEntity)


    @Query("SELECT * FROM book_details WHERE itemId = :itemId")
    suspend fun getBookDetails(itemId: String): BookDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewspaperDetails(details: NewspaperDetailsEntity)


    @Query("SELECT * FROM newspaper_details WHERE itemId = :itemId")
    suspend fun getNewspaperDetails(itemId: String): NewspaperDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiskDetails(details: DiskDetailsEntity)


    @Query("SELECT * FROM disk_details WHERE itemId = :itemId")
    suspend fun getDiskDetails(itemId: String): DiskDetailsEntity?


    @Query("SELECT COUNT(*) FROM base_items WHERE type = 'book'")
    suspend fun getBooksCount(): Int

    @Query("SELECT COUNT(*) FROM base_items WHERE type = 'newspaper'")
    suspend fun getNewspapersCount(): Int

    @Query("SELECT COUNT(*) FROM base_items WHERE type = 'disk'")
    suspend fun getDisksCount(): Int

    @Query("SELECT COUNT(*) FROM base_items")
    suspend fun getAllItemsCount(): Int

    @Query("SELECT * FROM base_items WHERE type = :type ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getBaseItemsByTypeWithLimit(
        type: String, offset: Int, limit: Int
    ): List<BaseItemEntity>

    @Query("SELECT * FROM base_items WHERE type = :type ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getBaseItemsByTypeSortedByName(
        type: String, offset: Int, limit: Int
    ): List<BaseItemEntity>

    @Query("SELECT * FROM base_items WHERE type = :type ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getBaseItemsByTypeSortedByDate(
        type: String, offset: Int, limit: Int
    ): List<BaseItemEntity>

    @Query("SELECT COUNT(*) FROM base_items WHERE (id = :id)")
    suspend fun isItemInDB(
        id: String?
    ): Boolean

}