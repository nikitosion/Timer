package com.memowave.timer

interface AlarmScheduler {
    fun schedule(endTimeMillis: Long): Boolean
    fun cancel()
}