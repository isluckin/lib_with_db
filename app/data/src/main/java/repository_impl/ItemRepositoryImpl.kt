package repository_impl


import com.example.lib_with_db.domain.repository.ItemRepository
import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.model.Disk
import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.model.Newspaper
import com.example.lib_with_db.domain.model.SortPreference
import com.example.lib_with_db.presentation.view_model.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import local.DAO.ItemDao
import local.DAO.SortPreferenceDao

class ItemRepositoryImpl(
    private val itemDao: ItemDao, private val sortPreferenceDao: SortPreferenceDao
) : ItemRepository {

    override suspend fun getSortPreference(): SortPreference = withContext(Dispatchers.IO) {
        sortPreferenceDao.getPreference()?.toDomain() ?: SortPreference.BY_NAME
    }

    override suspend fun saveSortPreference(sortPreference: SortPreference) =
        withContext(Dispatchers.IO) {
            sortPreferenceDao.insert(sortPreference.toEntity())
        }


    override suspend fun saveItem(item: Item) {
        val entity = item.toEntity()
        itemDao.insertBaseItem(entity.baseItemEntity)
        with(entity) {
            when (item) {
                is Book -> {
                    itemDao.insertBookDetails(details as BookDetailsEntity)
                }

                is Newspaper -> {
                    itemDao.insertNewspaperDetails(details as NewspaperDetailsEntity)
                }

                is Disk -> {
                    itemDao.insertDiskDetails(details as DiskDetailsEntity)
                }
            }
        }
    }

    override suspend fun getAllItemsCount(): Int = withContext(Dispatchers.IO) {
        return@withContext itemDao.getAllItemsCount()
    }

    override suspend fun getItemsSortedByName(offset: Int, limit: Int): List<Item> =
        withContext(Dispatchers.IO) {
            val books = getBooksSortedByName(offset, limit)
            val newspapers = getNewspapersSortedByName(offset, limit)
            val disks = getDisksSortedByName(offset, limit)
            return@withContext (books + newspapers + disks).sortedBy { it.itemName }
        }

    override suspend fun getItemsSortedByDate(offset: Int, limit: Int): List<Item> =
        withContext(Dispatchers.IO) {
            val books = getBooksSortedByDate(offset, limit)
            val newspapers = getNewspapersSortedByDate(offset, limit)
            val disks = getDisksSortedByDate(offset, limit)
            return@withContext (books + newspapers + disks).sortedByDescending { it.createdAt }
        }


    override suspend fun getBooksSortedByName(offset: Int, limit: Int): List<Book> =
        withContext(Dispatchers.IO) {
            itemDao.getBaseItemsByTypeSortedByName("book", offset, limit).mapNotNull { base ->
                itemDao.getBookDetails(base.id)?.let { details -> base.toDomain(details) }
            }

        }

    override suspend fun getBooksSortedByDate(offset: Int, limit: Int): List<Book> =
        withContext(Dispatchers.IO) {
            itemDao.getBaseItemsByTypeSortedByDate("book", offset, limit).mapNotNull { base ->
                itemDao.getBookDetails(base.id)?.let { details -> base.toDomain(details) }
            }

        }

    override suspend fun getNewspapersSortedByName(offset: Int, limit: Int): List<Newspaper> =
        withContext(Dispatchers.IO) {
            itemDao.getBaseItemsByTypeSortedByName("newspaper", offset, limit).mapNotNull { base ->
                itemDao.getNewspaperDetails(base.id)?.let { details -> base.toDomain(details) }
            }

        }

    override suspend fun getNewspapersSortedByDate(offset: Int, limit: Int): List<Newspaper> =
        withContext(Dispatchers.IO) {
            itemDao.getBaseItemsByTypeSortedByDate("newspaper", offset, limit).mapNotNull { base ->
                itemDao.getNewspaperDetails(base.id)?.let { details -> base.toDomain(details) }
            }

        }

    override suspend fun getDisksSortedByName(offset: Int, limit: Int): List<Disk> =
        withContext(Dispatchers.IO) {
            itemDao.getBaseItemsByTypeSortedByName("disk", offset, limit).mapNotNull { base ->
                itemDao.getDiskDetails(base.id)?.let { details -> base.toDomain(details) }
            }

        }

    override suspend fun getDisksSortedByDate(offset: Int, limit: Int): List<Disk> =
        withContext(Dispatchers.IO) {
            itemDao.getBaseItemsByTypeSortedByDate("disk", offset, limit).mapNotNull { base ->
                itemDao.getDiskDetails(base.id)?.let { details -> base.toDomain(details) }
            }

        }

    override suspend fun getItemsWithLimit(offset: Int, limit: Int): List<Item> {
        val books = getBooksWithLimit(offset, limit)
        val newspapers = getNewspapersWithLimit(offset - itemDao.getBooksCount(), limit)
        val disks = getDisksWithLimit(
            offset - itemDao.getBooksCount() - itemDao.getNewspapersCount(), limit
        )

        return (books + newspapers + disks).sortedByDescending { item ->
            when (sortPreferenceDao.getPreference()?.sortType) {
                "DATA" -> item.createdAt
                "NAME" -> item.itemName
                else -> item.createdAt
            }
        }
    }

    override suspend fun getBooksWithLimit(offset: Int, limit: Int): List<Book> =
        withContext(Dispatchers.IO) {
        itemDao.getBaseItemsByTypeWithLimit("book", offset, limit).mapNotNull { base ->
            itemDao.getBookDetails(base.id)?.let { details -> base.toDomain(details) }
        }
    }

    override suspend fun getNewspapersWithLimit(offset: Int, limit: Int): List<Newspaper> =
        withContext(Dispatchers.IO) {
        itemDao.getBaseItemsByTypeWithLimit("newspaper", offset, limit).mapNotNull { base ->
            itemDao.getNewspaperDetails(base.id)?.let { details -> base.toDomain(details) }
        }
    }

    override suspend fun getDisksWithLimit(offset: Int, limit: Int): List<Disk> =
        withContext(Dispatchers.IO) {
        itemDao.getBaseItemsByTypeWithLimit("disk", offset, limit).mapNotNull { base ->
            itemDao.getDiskDetails(base.id)?.let { details -> base.toDomain(details) }
        }
    }

    override suspend fun isItemInDB(itemId: String): Boolean {
        return itemDao.isItemInDB(itemId)
    }

}