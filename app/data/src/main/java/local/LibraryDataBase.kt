package local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import local.DAO.ItemDao
import local.DAO.SortPreferenceDao
import local.entity.BaseItemEntity
import local.entity.BookDetailsEntity
import local.entity.DiskDetailsEntity
import local.entity.NewspaperDetailsEntity
import local.entity.SortPreferenceEntity

@Database(
    entities = [BaseItemEntity::class, BookDetailsEntity::class, NewspaperDetailsEntity::class, DiskDetailsEntity::class, SortPreferenceEntity::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun sortPreferenceDao(): SortPreferenceDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "library.db"
            ).fallbackToDestructiveMigration().build()

        }
    }
}