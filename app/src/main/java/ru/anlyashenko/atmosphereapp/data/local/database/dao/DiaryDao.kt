package ru.anlyashenko.atmosphereapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.data.local.database.entity.DairyEntryDBO
import java.time.LocalDate

@Dao
interface DiaryDao {
    // Сохранение/обновление записи, если дата совпадает
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entry: DairyEntryDBO)

    // Flow со списком записей за нужный период
    @Query("SELECT * FROM diary_entries WHERE date >= :startDate AND date <= :endDate ORDER BY date DESC")
    fun getEntriesBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<DairyEntryDBO>>

    // Получение одной записи по дате
    @Query("SELECT * FROM diary_entries WHERE date = :date")
    suspend fun getEntryByDate(date: LocalDate): DairyEntryDBO?
}