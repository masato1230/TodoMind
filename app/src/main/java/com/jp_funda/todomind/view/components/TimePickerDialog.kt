package com.jp_funda.todomind.view.components

import android.content.res.Resources
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker

@Composable
fun TimePickerDialog(
    timeDialogState: MaterialDialogState,
    resources: Resources
) {
    val colorTheme = TimePickerDefaults.colors(
        activeBackgroundColor = Color.White,
        activeTextColor = Color.Black,
        inactiveBackgroundColor = Color(resources.getColor(R.color.aqua)),
        inactiveTextColor = Color.White,
        selectorColor = Color(resources.getColor(R.color.teal_200)),
        borderColor = Color(resources.getColor(R.color.navy_blue))
    )

    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = Color(resources.getColor(R.color.navy_blue)),
        buttons = {
            positiveButton(
                "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = Color(resources.getColor(R.color.teal_200)),
                )
            )
            negativeButton(
                "Cancel",
                textStyle = MaterialTheme.typography.button.copy(
                    color = Color(resources.getColor(R.color.teal_200)),
                )
            )
        },
    ) {
        timepicker(
            colors = colorTheme
        ) { time ->
            // Todo save time
        }
    }
}