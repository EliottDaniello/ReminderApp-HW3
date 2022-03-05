package com.codemave.reminderapp.ui.addReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.reminderapp.Data.Entity.Reminder
import com.codemave.reminderapp.Data.Repository.ReminderRepository
import com.codemave.reminderapp.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditReminderViewModel(
    private val reminderId: Long,
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
): ViewModel() {
    private val _state = MutableStateFlow(EditReminderViewState(null))

    suspend fun editReminder(reminder: Reminder) {
        return reminderRepository.editReminder(reminder)
    }

    suspend fun deleteReminder(id :Long){
        reminderRepository.deleteReminder(id)
    }

    suspend fun getReminder(id :Long): Reminder?{
        return  reminderRepository.getReminder(id)
    }

    init {
        viewModelScope.launch {
            reminderRepository.getReminder(reminderId).apply {
                _state.value = EditReminderViewState(this)
            }
        }
    }
}

data class EditReminderViewState(
    val reminder: Reminder?
)