package com.memowave.timer.ui.observe_timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NumberPicker(
    modifier: Modifier = Modifier,
    value: Int = 0,
    addValue: () -> Unit,
    subtractValue: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(end = 12.dp),
            text = value.toString(),
            style = MaterialTheme.typography.displayMedium
        )
        Column() {
            Button(
                onClick = addValue
            ) { Text(text = "+") }
            Button(
                onClick = subtractValue
            ) { Text(text = "-") }
        }
    }
}

@Preview
@Composable
fun NumberPickerPreview() {
    NumberPicker(
        addValue = {},
        subtractValue = {}
    )
}