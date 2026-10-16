package com.memowave.timer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memowave.timer.ui.observe_timer.NumberPicker

@Composable
fun MainScreenRoute(navController: NavController, timerVM: TimerViewModel) {
    MainScreen(navController, timerVM)
}

@Composable
fun MainScreen(
    navController: NavController,
    timerVM: TimerViewModel
) {
    val uiState = timerVM.uiState.collectAsState().value
    val (hours, minutes, seconds) = timerVM.getTimeUnits(uiState.remainingMillis)

    MainScreenContent(
        phase = uiState.phase,
        hours = hours,
        minutes = minutes,
        seconds = seconds,
        onChangedTime = { timeUnitOperation, changedTime ->
            timerVM.updateTime(timeUnitOperation, changedTime)
        },
        updatePhase = { newPhase ->
            timerVM.updatePhase(newPhase)
        }
    )
}

@Composable
fun MainScreenContent(
    phase: TimerPhase = TimerPhase.STOPPED,
    hours: Long = 0,
    minutes: Long = 0,
    seconds: Long = 1,
    onChangedTime: (TimeUnitOperation, ChangedTimeUnit) -> Unit,
    updatePhase: (TimerPhase) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            NumberPicker(
                value = hours,
                addValue = { onChangedTime(TimeUnitOperation.INCREMENT, ChangedTimeUnit.HOURS) },
                subtractValue = {
                    onChangedTime(
                        TimeUnitOperation.DECREMENT,
                        ChangedTimeUnit.HOURS
                    )
                }
            )
            NumberPicker(
                value = minutes,
                addValue = { onChangedTime(TimeUnitOperation.INCREMENT, ChangedTimeUnit.MINUTES) },
                subtractValue = {
                    onChangedTime(
                        TimeUnitOperation.DECREMENT,
                        ChangedTimeUnit.MINUTES
                    )
                }
            )
            NumberPicker(
                value = seconds,
                addValue = { onChangedTime(TimeUnitOperation.INCREMENT, ChangedTimeUnit.SECONDS) },
                subtractValue = {
                    onChangedTime(
                        TimeUnitOperation.DECREMENT,
                        ChangedTimeUnit.SECONDS
                    )
                }
            )
        }

        when (phase) {
            TimerPhase.RUNNING -> {
                OutlinedButton(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { updatePhase(TimerPhase.PAUSED) }
                ) {
                    Text(text = "Pause Timer")
                }
            }

            TimerPhase.PAUSED -> {
                Row() {
                    OutlinedButton(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { updatePhase(TimerPhase.RUNNING) }
                    ) {
                        Text(text = "Resume Timer")
                    }
                    Button(
                        modifier = Modifier.padding(top = 16.dp, start = 8.dp),
                        onClick = { updatePhase(TimerPhase.STOPPED) }
                    ) {
                        Text(text = "Stop Timer")
                    }
                }
            }

            TimerPhase.STOPPED -> {
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = { updatePhase(TimerPhase.RUNNING) }
                ) {
                    Text(text = "Start Timer")
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        phase = TimerPhase.PAUSED,
        hours = 1,
        minutes = 30,
        seconds = 45,
        onChangedTime = { _, _ -> },
        updatePhase = {}
    )
}