package com.example.fridgeui.ui.theme.model

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "history_list")
data class HistoryElement(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val product: String,
    var amount: Float
)
@Dao
interface HistoryDao {
    @Query("SELECT * FROM history_list ORDER BY product ASC")
    fun getHistory(): Flow<List<HistoryElement>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: HistoryElement)

    @Query("DELETE FROM history_list")
    suspend fun deleteAll()

    @Update
    suspend fun update(history: HistoryElement)

    @Delete
    suspend fun delete(history: HistoryElement)
}

@Database(entities = [HistoryElement::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var Instance: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HistoryDatabase::class.java, "history_list")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}