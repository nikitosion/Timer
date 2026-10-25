package com.memowave.timer

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

class TimerAlarmScheduler(context: Context) : AlarmScheduler {
    private val appContext = context.applicationContext

    private val alarmManager = appContext.getSystemService(AlarmManager::class.java)

    private fun pendingIntent(): PendingIntent {
        return PendingIntent.getBroadcast(
            appContext,
            0,
            Intent(appContext, TimerAlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun schedule(endTimeMillis: Long): Boolean {
        return try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                endTimeMillis,
                pendingIntent()
            )
            true
        } catch (_: SecurityException) {
            false
        }
    }

    override fun cancel() {
        alarmManager.cancel(pendingIntent())
    }

    fun canSchedule(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms()
    }
}