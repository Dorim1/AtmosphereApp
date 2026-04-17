package ru.anlyashenko.atmosphereapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.data.local.database.entity.DiaryEntryDBO
import java.time.LocalDate

@Dao
interface DiaryDao {
    // Сохранение/обновление записи, если дата совпадает
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entry: DiaryEntryDBO)

    // Flow со списком записей за нужный период
    @Query("SELECT * FROM diary_entries WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getEntriesBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<DiaryEntryDBO>>

    // Flow со списком всех записей
    @Query("SELECT * FROM diary_entries")
    fun getAllEntries(): Flow<List<DiaryEntryDBO>>

    // Получение одной записи по дате
    @Query("SELECT * FROM diary_entries WHERE date = :date")
    suspend fun getEntryByDate(date: LocalDate): DiaryEntryDBO?

    // Замена всех записей с одним настроением на другое
    @Query("UPDATE diary_entries SET moodId = :targetMoodId WHERE moodId = :oldMoodId")
    suspend fun replaceMoodInAllEntries(oldMoodId: Int, targetMoodId: Int)

    @Delete
    suspend fun delete(entry: DiaryEntryDBO)
}