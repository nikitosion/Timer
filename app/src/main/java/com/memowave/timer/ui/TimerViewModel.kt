package com.memowave.timer.ui

import android.os.SystemClock
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class ChangedTimeUnit {
    HOURS, MINUTES, SECONDS
}

enum class TimeUnitOperation {
    INCREMENT, DECREMENT
}

enum class TimerPhase {
    RUNNING, PAUSED, STOPPED
}

enum class TimeUnitInMillis(val millis: Long) {
    HOUR(3600000L),
    MINUTE(60000L),
    SECOND(1000L);
}

class TimerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState = _uiState.asStateFlow()

    private var tickJob: Job? = null
    private var endTimeMillis: Long = 0L

    fun updatePhase(newPhase: TimerPhase) {
        if (newPhase == _uiState.value.phase) return

        when (newPhase) {
            TimerPhase.RUNNING -> startOrResumeTimer()
            TimerPhase.PAUSED -> pauseTimer()
            TimerPhase.STOPPED -> stopTimer()
        }
    }

    fun startOrResumeTimer() {
        val curState = _uiState.value
        if (curState.phase == TimerPhase.RUNNING) return

        val duration = curState.remainingMillis
        if (duration <= 0) {
            return
        }

        tickJob?.cancel()
        endTimeMillis = SystemClock.elapsedRealtime() + duration

        _uiState.update {
            it.copy(phase = TimerPhase.RUNNING)
        }

        tickJob = viewModelScope.launch {
            while (isActive) {
                val remainingMillis = (endTimeMillis - SystemClock.elapsedRealtime()).coerceAtLeast(0L)
                _uiState.update {
                    it.copy(
                        remainingMillis = remainingMillis,
                        phase = if (remainingMillis > 0) {
                            TimerPhase.RUNNING
                        } else {
                            TimerPhase.STOPPED
                        }
                    )
                }

                if (remainingMillis <= 0) {
                    stopTimer()
                    finishNotify()
                    break
                }
                delay(TimeUnitInMillis.SECOND.millis)
            }
        }
    }

    fun pauseTimer() {
        val curState = _uiState.value
        if (curState.phase != TimerPhase.RUNNING) return

        tickJob?.cancel()
        tickJob = null

        val remainingMillis = endTimeMillis - SystemClock.elapsedRealtime()
        _uiState.update {
            it.copy(
                remainingMillis = remainingMillis,
                phase = if (remainingMillis > 0) {
                    TimerPhase.PAUSED
                } else {
                    TimerPhase.STOPPED
                }
            )
        }

        if (remainingMillis <= 0) {
            stopTimer()
            finishNotify()
        }
    }

    fun stopTimer() {
        tickJob?.cancel()
        tickJob = null

        _uiState.update {
            it.copy(
                remainingMillis = 0L,
                phase = TimerPhase.STOPPED
            )
        }
    }

    fun finishNotify() {

    }

    fun updateTime(newValue: TimeUnitOperation, changedTime: ChangedTimeUnit) {
        when (changedTime) {
            ChangedTimeUnit.HOURS -> _uiState.update {
                it.copy(remainingMillis = it.remainingMillis + if (newValue == TimeUnitOperation.INCREMENT) TimeUnitInMillis.HOUR.millis else -TimeUnitInMillis.HOUR.millis)
            }

            ChangedTimeUnit.MINUTES -> _uiState.update {
                it.copy(remainingMillis = it.remainingMillis + if (newValue == TimeUnitOperation.INCREMENT) TimeUnitInMillis.MINUTE.millis else -TimeUnitInMillis.MINUTE.millis)
            }

            ChangedTimeUnit.SECONDS -> _uiState.update {
                it.copy(remainingMillis = it.remainingMillis + if (newValue == TimeUnitOperation.INCREMENT) TimeUnitInMillis.SECOND.millis else -TimeUnitInMillis.SECOND.millis)
            }
        }
    }

    fun getTimeUnits(timeMillis: Long): Triple<Long, Long, Long> {
        val totalSeconds = (timeMillis.coerceAtLeast(0L) + 999L) / 1000L
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return Triple(hours, minutes, seconds)
    }
}