package repository_impl.mapper



import com.example.lib_with_db.domain.model.Book
import com.example.lib_with_db.domain.model.Disk
import com.example.lib_with_db.domain.model.Item
import com.example.lib_with_db.domain.model.Newspaper
import local.entity.BaseItemEntity
import local.entity.BookDetailsEntity
import local.entity.DiskDetailsEntity
import local.entity.NewspaperDetailsEntity

internal fun BaseItemEntity.toDomain(bookDetailsEntity: BookDetailsEntity): Book = Book(

    itemId = id,
    itemName = name,
    bookAuthor = bookDetailsEntity.author,
    bookPages = bookDetailsEntity.pageCount,
    isAvailable = isAvailable,
    imageUrl = bookDetailsEntity.imageUrl,
    createdAt = createdAt
)

internal fun BaseItemEntity.toDomain(newspaperDetailsEntity: NewspaperDetailsEntity): Newspaper = Newspaper(

    itemId = id,
    itemName = name.toString(),
    newspaperNumber = newspaperDetailsEntity.newspaperNumber,
    month = newspaperDetailsEntity.month,
    isAvailable = isAvailable,
    createdAt = createdAt.toString()
)

internal fun BaseItemEntity.toDomain(diskDetailsEntity: DiskDetailsEntity): Disk = Disk(

    itemId = id,
    itemName = name.toString(),
    diskType = type,
    isAvailable = isAvailable,
    createdAt = createdAt.toString()
)

internal fun Item.toEntity() : ItemEntityWrapper{
 return   when(this)
    {
        is Book -> {
                val baseItemEntity = BaseItemEntity(
                    id = itemId,
                    name =  itemName,
                    isAvailable = isAvailable,
                    createdAt = createdAt,
                    type = "book"
                )
            val bookDetailsEntity = BookDetailsEntity(
                itemId = itemId,
                author = bookAuthor,
                pageCount = bookPages,
                imageUrl = imageUrl
            )
            ItemEntityWrapper(baseItemEntity, bookDetailsEntity)

        }
        is Newspaper -> {
            val baseItemEntity = BaseItemEntity(
                id =  itemId,
                type = "newspaper",
                name = itemName,
                isAvailable = isAvailable,
                createdAt = createdAt
            )


            val newspaperDetailsEntity = NewspaperDetailsEntity(
                itemId = itemId,
                month = month,
                newspaperNumber = newspaperNumber
            )
            ItemEntityWrapper(baseItemEntity, newspaperDetailsEntity)
        }
        is Disk -> {
            val baseItemEntity = BaseItemEntity(
                id = itemId,
                type = "disk",
                name = itemName,
                isAvailable = isAvailable,
                createdAt = createdAt
            )

            val diskDetailsEntity = DiskDetailsEntity(
                itemId = itemId,
                diskType = diskType,

                )
            ItemEntityWrapper(baseItemEntity, diskDetailsEntity)
        }
    }
}
data class ItemEntityWrapper(
    val baseItemEntity: BaseItemEntity,
    val details: Any
)