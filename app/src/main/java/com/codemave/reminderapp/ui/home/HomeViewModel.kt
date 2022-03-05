package com.codemave.reminderapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.reminderapp.Data.Entity.Reminder
import com.codemave.reminderapp.Data.Repository.ReminderRepository
import com.codemave.reminderapp.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
        viewModelScope.launch {
            reminderRepository.reminders().collect { reminders ->
                    _state.value = HomeViewState(reminders)
            }
        }
    }
}

data class HomeViewState(
    var reminders: List<Reminder> = emptyList()
)