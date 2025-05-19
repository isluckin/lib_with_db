package local.DAO


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import local.entity.SortPreferenceEntity


@Dao
interface SortPreferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preference: SortPreferenceEntity)

    @Query("SELECT * FROM sort_preference LIMIT 1")
     fun getPreference(): SortPreferenceEntity?
}