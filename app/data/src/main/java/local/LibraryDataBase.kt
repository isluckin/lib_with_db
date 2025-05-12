package local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lib_with_db.data.local.DAO.ItemDao
import com.example.lib_with_db.data.local.DAO.SortPreferenceDao
import com.example.lib_with_db.data.local.entity.BaseItemEntity
import com.example.lib_with_db.data.local.entity.BookDetailsEntity
import com.example.lib_with_db.data.local.entity.DiskDetailsEntity
import com.example.lib_with_db.data.local.entity.NewspaperDetailsEntity
import com.example.lib_with_db.data.local.entity.SortPreferenceEntity

@Database(
    entities = [BaseItemEntity::class, BookDetailsEntity::class, NewspaperDetailsEntity::class, DiskDetailsEntity::class, SortPreferenceEntity::class],
    version = 9,
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