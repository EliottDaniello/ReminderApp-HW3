package com.codemave.reminderapp

import android.content.Context
import androidx.room.Room
import com.codemave.reminderapp.Data.Repository.ReminderRepository
import com.codemave.reminderapp.Data.Room.ReminderAppDatabase

object Graph {

    lateinit var database: ReminderAppDatabase
        private set

    lateinit var appContext: Context

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    fun provide(context: Context) {
        appContext = context
        database = Room.databaseBuilder(context, ReminderAppDatabase::class.java, "mcData.db")
            .fallbackToDestructiveMigration() // don't use this in production app
            .build()
    }
}