package com.memowave.timer.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ChangedTimeUnit {
    HOURS, MINUTES, SECONDS
}

enum class TimeUnitOperation {
    INCREMENT, DECREMENT
}

class TimerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    fun updateTime(newValue: TimeUnitOperation, changedTime: ChangedTimeUnit) {
        when (changedTime) {
            ChangedTimeUnit.HOURS -> _uiState.update {
                it.copy(hours = it.hours + if (newValue == TimeUnitOperation.INCREMENT) 1 else -1)
            }

            ChangedTimeUnit.MINUTES -> _uiState.update {
                it.copy(minutes = it.minutes + if (newValue == TimeUnitOperation.INCREMENT) 1 else -1)
            }

            ChangedTimeUnit.SECONDS -> _uiState.update {
                it.copy(seconds = it.seconds + if (newValue == TimeUnitOperation.INCREMENT) 1 else -1)
            }
        }
    }
}