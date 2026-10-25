package com.memowave.timer

interface TimerStateStore {
    fun loadEndTime(): Long?
    fun saveEndTime(endTimeMillis: Long)
    fun clear()
}