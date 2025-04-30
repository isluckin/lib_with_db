package com.example.lib_with_db


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(
    private val itemDao: ItemDao, private val sortPreferenceDao: SortPreferenceDao
) {

    suspend fun getSortPreference(): ItemViewModel.SortType = withContext(Dispatchers.IO) {
        val preference = sortPreferenceDao.getPreference()
        return@withContext when (preference?.sortType) {
            "NAME" -> ItemViewModel.SortType.BY_NAME
            else -> ItemViewModel.SortType.BY_DATE
        }
    }

    suspend fun saveSortPreference(sortType: ItemViewModel.SortType) = withContext(Dispatchers.IO) {
        val preference = SortPreference(
            sortType = when (sortType) {
                ItemViewModel.SortType.BY_NAME -> "NAME"
                ItemViewModel.SortType.BY_DATE -> "DATE"
            }
        )
        sortPreferenceDao.insert(preference)
    }


    suspend fun saveBook(book: Book) = withContext(Dispatchers.IO) {
        val baseItem = BaseItemEntity(
            id = book.itemId,
            type = "book",
            name = book.itemName,
            isAvailable = book.isAvailable,
            imageRes = book.imageRes,
            createdAt = book.createdAt
        )

        val details = BookDetailsEntity(
            itemId = book.itemId, author = book.bookAuthor, pageCount = book.bookPages
        )

        itemDao.insertBaseItem(baseItem)
        itemDao.insertBookDetails(details)
    }

    suspend fun saveNewspaper(newspaper: Newspaper) = withContext(Dispatchers.IO) {
        val baseItem = BaseItemEntity(
            id = newspaper.itemId,
            type = "newspaper",
            name = newspaper.itemName,
            isAvailable = newspaper.isAvailable,
            imageRes = newspaper.imageRes,
            createdAt = newspaper.createdAt
        )

        val details = NewspaperDetailsEntity(
            itemId = newspaper.itemId,
            month = newspaper.month,
            newspaperNumber = newspaper.newspaperNumber
        )

        itemDao.insertBaseItem(baseItem)
        itemDao.insertNewspaperDetails(details)
    }

    suspend fun saveDisk(disk: Disk) = withContext(Dispatchers.IO) {
        val baseItem = BaseItemEntity(
            id = disk.itemId,
            type = "disk",
            name = disk.itemName,
            isAvailable = disk.isAvailable,
            imageRes = disk.imageRes,
            createdAt = disk.createdAt
        )

        val details = DiskDetailsEntity(
            itemId = disk.itemId,
            diskType = disk.diskType,

            )

        itemDao.insertBaseItem(baseItem)
        itemDao.insertDiskDetails(details)
    }


    suspend fun getAllItemsCount(): Int = withContext(Dispatchers.IO) {
        return@withContext itemDao.getAllItemsCount()
    }

    suspend fun getItemsSortedByName(offset: Int, limit: Int): List<Item> =
        withContext(Dispatchers.IO) {
            val books = getBooksSortedByName(offset, limit)
            val newspapers = getNewspapersSortedByName(offset, limit)
            val disks = getDisksSortedByName(offset, limit)
            return@withContext (books + newspapers + disks).sortedBy { it.itemName }
        }

    suspend fun getItemsSortedByDate(offset: Int, limit: Int): List<Item> =
        withContext(Dispatchers.IO) {
            val books = getBooksSortedByDate(offset, limit)
            val newspapers = getNewspapersSortedByDate(offset, limit)
            val disks = getDisksSortedByDate(offset, limit)
            return@withContext (books + newspapers + disks).sortedByDescending { it.createdAt }
        }


    private suspend fun getBooksSortedByName(offset: Int, limit: Int): List<Book> =
        withContext(Dispatchers.IO) {
            val baseItems = itemDao.getBaseItemsByTypeSortedByName("book", offset, limit)
            return@withContext baseItems.mapNotNull { base ->
                val details = itemDao.getBookDetails(base.id) ?: return@mapNotNull null
                Book(
                    itemId = base.id,
                    itemName = base.name,
                    bookAuthor = details.author,
                    bookPages = details.pageCount,
                    isAvailable = base.isAvailable,
                    imageRes = base.imageRes,
                    createdAt = base.createdAt
                )
            }
        }

    private suspend fun getBooksSortedByDate(offset: Int, limit: Int): List<Book> =
        withContext(Dispatchers.IO) {
            val baseItems = itemDao.getBaseItemsByTypeSortedByDate("book", offset, limit)
            return@withContext baseItems.mapNotNull { base ->
                val details = itemDao.getBookDetails(base.id) ?: return@mapNotNull null
                Book(
                    itemId = base.id,
                    itemName = base.name,
                    bookAuthor = details.author,
                    bookPages = details.pageCount,
                    isAvailable = base.isAvailable,
                    imageRes = base.imageRes,
                    createdAt = base.createdAt
                )
            }
        }

    private suspend fun getNewspapersSortedByName(offset: Int, limit: Int): List<Newspaper> =
        withContext(Dispatchers.IO) {
            val baseItems = itemDao.getBaseItemsByTypeSortedByName("newspaper", offset, limit)
            return@withContext baseItems.mapNotNull { base ->
                val details = itemDao.getNewspaperDetails(base.id) ?: return@mapNotNull null
                Newspaper(
                    itemId = base.id,
                    itemName = base.name,
                    newspaperNumber = details.newspaperNumber,
                    month = details.month,
                    isAvailable = base.isAvailable,
                    imageRes = base.imageRes,
                    createdAt = base.createdAt
                )
            }
        }

    private suspend fun getNewspapersSortedByDate(offset: Int, limit: Int): List<Newspaper> =
        withContext(Dispatchers.IO) {
            val baseItems = itemDao.getBaseItemsByTypeSortedByDate("newspaper", offset, limit)
            return@withContext baseItems.mapNotNull { base ->
                val details = itemDao.getNewspaperDetails(base.id) ?: return@mapNotNull null
                Newspaper(
                    itemId = base.id,
                    itemName = base.name,
                    newspaperNumber = details.newspaperNumber,
                    month = details.month,
                    isAvailable = base.isAvailable,
                    imageRes = base.imageRes,
                    createdAt = base.createdAt
                )
            }
        }

    private suspend fun getDisksSortedByName(offset: Int, limit: Int): List<Disk> =
        withContext(Dispatchers.IO) {
            val baseItems = itemDao.getBaseItemsByTypeSortedByName("disk", offset, limit)
            return@withContext baseItems.mapNotNull { base ->
                val details = itemDao.getDiskDetails(base.id) ?: return@mapNotNull null
                Disk(
                    itemId = base.id,
                    itemName = base.name,
                    diskType = details.diskType,
                    isAvailable = base.isAvailable,
                    imageRes = base.imageRes,
                    createdAt = base.createdAt
                )
            }
        }

    private suspend fun getDisksSortedByDate(offset: Int, limit: Int): List<Disk> =
        withContext(Dispatchers.IO) {
            val baseItems = itemDao.getBaseItemsByTypeSortedByDate("disk", offset, limit)
            return@withContext baseItems.mapNotNull { base ->
                val details = itemDao.getDiskDetails(base.id) ?: return@mapNotNull null
                Disk(
                    itemId = base.id,
                    itemName = base.name,
                    diskType = details.diskType,
                    isAvailable = base.isAvailable,
                    imageRes = base.imageRes,
                    createdAt = base.createdAt
                )
            }
        }

    suspend fun getItemsWithLimit(offset: Int, limit: Int): List<Item> {
        val books = getBooksWithLimit(offset, limit)
        val newspapers = getNewspapersWithLimit(offset - itemDao.getBooksCount(), limit)
        val disks = getDisksWithLimit(
            offset - itemDao.getBooksCount() - itemDao.getNewspapersCount(), limit
        )

        return (books + newspapers + disks).sortedByDescending { it.createdAt }
    }

    private suspend fun getBooksWithLimit(offset: Int, limit: Int): List<Book> {
        val baseItems = itemDao.getBaseItemsByTypeWithLimit("book", offset, limit)
        return baseItems.mapNotNull { base ->
            val details = itemDao.getBookDetails(base.id) ?: return@mapNotNull null
            Book(
                itemId = base.id,
                itemName = base.name,
                bookAuthor = details.author,
                bookPages = details.pageCount,
                isAvailable = base.isAvailable,
                imageRes = base.imageRes,
                createdAt = base.createdAt
            )
        }
    }

    private suspend fun getNewspapersWithLimit(offset: Int, limit: Int): List<Newspaper> {
        val baseItems = itemDao.getBaseItemsByTypeWithLimit("newspaper", offset, limit)
        return baseItems.mapNotNull { base ->
            val details = itemDao.getNewspaperDetails(base.id) ?: return@mapNotNull null
            Newspaper(
                itemId = base.id,
                itemName = base.name,
                newspaperNumber = details.newspaperNumber,
                month = details.month,
                isAvailable = base.isAvailable,
                imageRes = base.imageRes,
                createdAt = base.createdAt
            )
        }
    }

    private suspend fun getDisksWithLimit(offset: Int, limit: Int): List<Disk> {
        val baseItems = itemDao.getBaseItemsByTypeWithLimit("disk", offset, limit)
        return baseItems.mapNotNull { base ->
            val details = itemDao.getDiskDetails(base.id) ?: return@mapNotNull null
            Disk(
                itemId = base.id,
                itemName = base.name,
                diskType = details.diskType,
                isAvailable = base.isAvailable,
                imageRes = base.imageRes,
                createdAt = base.createdAt
            )
        }
    }

    suspend fun fillLargeData() {
        if (itemDao.getAllItemsCount() > 5) return

        val allItems = (1..100).map { i ->
            when (i % 3) {
                0 -> BaseItemEntity(
                    id = i,
                    name = "Book $i",
                    type = "book",
                    createdAt = System.currentTimeMillis(),
                    isAvailable = true,
                    imageRes = R.drawable.book_image
                ).also { baseItem ->
                    itemDao.insertBookDetails(
                        BookDetailsEntity(
                            itemId = i,
                            author = "Author ${i % 10}",
                            pageCount = i * 100,
                        )
                    )
                }

                1 -> BaseItemEntity(
                    id = i,
                    name = "News $i",
                    type = "newspaper",
                    createdAt = System.currentTimeMillis(),
                    isAvailable = true,
                    imageRes = R.drawable.newspaper_image
                ).also { baseItem ->
                    itemDao.insertNewspaperDetails(
                        NewspaperDetailsEntity(
                            itemId = i, month = "${i % 3 + 1}", newspaperNumber = i + i
                        )
                    )
                }

                else -> BaseItemEntity(
                    id = i,
                    name = "Disk $i",
                    type = "disk",
                    createdAt = System.currentTimeMillis(),
                    isAvailable = true,
                    imageRes = R.drawable.disk_image
                ).also { baseItem ->
                    itemDao.insertDiskDetails(
                        DiskDetailsEntity(
                            itemId = i, diskType = if (i % 2 == 0) "CD" else "DVD"
                        )
                    )
                }
            }
        }

        allItems.forEach { item ->
            itemDao.insertBaseItem(item)
        }
    }
}