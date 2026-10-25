package com.memowave.timer.ui

data class TimerUiState(
    val phase: TimerPhase = TimerPhase.STOPPED,
    val remainingMillis: Long = TimeUnitInMillis.SECOND.millis
)