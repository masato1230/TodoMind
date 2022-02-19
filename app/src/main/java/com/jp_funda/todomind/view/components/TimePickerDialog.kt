package com.jp_funda.todomind.view.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

@Composable
fun TimePickerDialog(
    timeDialogState: MaterialDialogState,
    isEdit: Boolean = false,
    onReset: () -> Unit = {},
    onSelected: (localTime: LocalTime) -> Unit,
) {
    var time = LocalTime.now()

    val colorTheme = TimePickerDefaults.colors(
        activeBackgroundColor = Color.White,
        activeTextColor = Color.Black,
        inactiveBackgroundColor = Color(ContextCompat.getColor(LocalContext.current, R.color.aqua)),
        inactiveTextColor = Color.White,
        selectorColor = Color(ContextCompat.getColor(LocalContext.current, R.color.teal_200)),
        borderColor = Color(ContextCompat.getColor(LocalContext.current, R.color.navy_blue))
    )

    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = Color(ContextCompat.getColor(LocalContext.current, R.color.navy_blue)),
        buttons = {
            positiveButton(
                "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = Color(ContextCompat.getColor(LocalContext.current, R.color.teal_200)),
                ),
                onClick = {
                    onSelected(time)
                }
            )
            if (!isEdit) {
                negativeButton(
                    "Cancel",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.teal_200)),
                    )
                )
            } else {
                negativeButton(
                    "Reset",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = Color(ContextCompat.getColor(LocalContext.current, R.color.teal_200)),
                    )
                ) { onReset() }
            }
        },
    ) {
        timepicker(
            colors = colorTheme
        ) {
            time = it
        }
    }
}