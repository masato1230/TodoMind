package com.jp_funda.todomind.view.components.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
        inactiveBackgroundColor = colorResource(id = R.color.aqua),
        inactiveTextColor = Color.White,
        selectorColor = colorResource(R.color.teal_200),
        borderColor = colorResource(R.color.navy_blue)
    )

    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = colorResource(R.color.navy_blue),
        buttons = {
            positiveButton(
                "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = colorResource(R.color.teal_200),
                ),
                onClick = {
                    onSelected(time)
                }
            )
            if (!isEdit) {
                negativeButton(
                    "Cancel",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = colorResource(id = R.color.teal_200),
                    )
                )
            } else {
                negativeButton(
                    "Reset",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = colorResource(id = R.color.teal_200),
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