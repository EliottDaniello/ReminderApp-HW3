package com.codemave.reminderapp.Data.Repository

import com.codemave.reminderapp.Data.Room.ReminderDao
import com.codemave.reminderapp.Data.Entity.Reminder
import kotlinx.coroutines.flow.Flow

/**
 * A data repository for [Reminder] instances
 */
class ReminderRepository (
    private val reminderDao: ReminderDao
){
    /**
     * Returns a flow containing the list of reminders
     */
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()

    /**
     * Add a new [Reminder] to the reminder store
     */
    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)

    /**
     * Delete a [Reminder] to the reminder store with the reminder id
     */
    suspend fun deleteReminder(id: Long) = reminderDao.delete(id)

    /**
     * Edit a [Reminder] to the reminder store with the reminder id
     */
    suspend fun editReminder(reminder: Reminder) = reminderDao.update(reminder)

    /**
     * Get a [Reminder] from the reminder store with the reminder id
     */
    suspend fun getReminder(id: Long) = reminderDao.reminder(id)

    /**
     * Set reminder_seen attribute to 1 with the reminder id
     */
    suspend fun setSeen(id: Long) = reminderDao.setSeen(id)
}