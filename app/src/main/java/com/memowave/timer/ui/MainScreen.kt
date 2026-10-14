package com.memowave.timer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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

    MainScreenContent(
        hours = uiState.hours,
        minutes = uiState.minutes,
        seconds = uiState.seconds,
        onChangedTime = { timeUnitOperation, changedTime ->
            timerVM.updateTime(timeUnitOperation, changedTime)
        }
    )
}

@Composable
fun MainScreenContent(
    hours: Int = 0,
    minutes: Int = 0,
    seconds: Int = 0,
    onChangedTime: (TimeUnitOperation, ChangedTimeUnit) -> Unit
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
                subtractValue = { onChangedTime(TimeUnitOperation.DECREMENT, ChangedTimeUnit.HOURS) }
            )
            NumberPicker(
                value = minutes,
                addValue = { onChangedTime(TimeUnitOperation.INCREMENT, ChangedTimeUnit.MINUTES) },
                subtractValue = { onChangedTime(TimeUnitOperation.DECREMENT, ChangedTimeUnit.MINUTES) }
            )
            NumberPicker(
                value = seconds,
                addValue = { onChangedTime(TimeUnitOperation.INCREMENT, ChangedTimeUnit.SECONDS) },
                subtractValue = { onChangedTime(TimeUnitOperation.DECREMENT, ChangedTimeUnit.SECONDS) }
            )
        }

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {}
        ) {
            Text(text = "Start Timer")
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        hours = 1,
        minutes = 30,
        seconds = 45,
        onChangedTime = { _, _ -> }
    )
}