package com.codemave.reminderapp.Data.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codemave.reminderapp.Data.Entity.Reminder


@Database(
    entities = [Reminder::class],
    version = 4,
    exportSchema = false
)

abstract class ReminderAppDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}