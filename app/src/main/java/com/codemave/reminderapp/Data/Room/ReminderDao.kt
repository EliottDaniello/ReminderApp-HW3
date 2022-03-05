package com.codemave.reminderapp.Data.Room

import androidx.room.*
import com.codemave.reminderapp.Data.Entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao{

    @Query("SELECT * FROM reminders")
    abstract fun reminders(): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int

    @Query("DELETE FROM reminders WHERE id = :id")
    abstract suspend fun delete(id: Long)

    @Query("""SELECT * FROM reminders WHERE id = :id""")
    abstract suspend fun reminder(id: Long): Reminder?

    @Query("UPDATE reminders SET reminder_seen=1 WHERE id = :id")
    abstract suspend fun setSeen(id: Long)
}