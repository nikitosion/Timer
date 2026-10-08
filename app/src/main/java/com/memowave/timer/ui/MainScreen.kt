package com.memowave.timer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.memowave.timer.ui.observe_timer.NumberPicker

@Composable
fun MainScreenRoute(navController: NavController) {
    MainScreen(navController)
}

@Composable
fun MainScreen(
    navController: NavController
) {
    MainScreenContent()
}

@Composable
fun MainScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            NumberPicker(
                addValue = {},
                subtractValue = {}
            )
            NumberPicker(
                addValue = {},
                subtractValue = {}
            )
            NumberPicker(
                addValue = {},
                subtractValue = {}
            )
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreenContent()
}