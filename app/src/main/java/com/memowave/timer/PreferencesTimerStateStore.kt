package com.memowave.timer

import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesTimerStateStore(
    private val preferences: SharedPreferences
) : TimerStateStore {
    override fun loadEndTime(): Long? {
        return if (preferences.contains("end_time")) {
            preferences.getLong("end_time", 0L)
        } else {
            null
        }
    }

    override fun saveEndTime(endTimeMillis: Long) {
        preferences.edit { putLong("end_time", endTimeMillis) }
    }

    override fun clear() {
        preferences.edit { remove("end_time") }
    }

}