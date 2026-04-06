package ru.anlyashenko.atmosphereapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.data.local.database.entity.MoodDBO

@Dao
interface MoodDao {
    @Query("SELECT * FROM moods ORDER BY id ASC")
    fun getAllMoods(): Flow<List<MoodDBO>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoods(moods: List<MoodDBO>)

    @Update
    suspend fun updateMood(mood: MoodDBO)
}