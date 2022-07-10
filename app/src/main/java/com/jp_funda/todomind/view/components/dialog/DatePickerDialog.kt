package com.jp_funda.todomind.view.components.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    dateDialogState: MaterialDialogState,
    initialValue: LocalDate = LocalDate.now(),
    isEdit: Boolean = false,
    onReset: () -> Unit = {},
    onSelected: (LocalDate) -> Unit,
) {
    var date: LocalDate = initialValue

    val colorTheme = DatePickerDefaults.colors(
        headerBackgroundColor = colorResource(R.color.aqua),
        headerTextColor = Color.White,
        dateActiveBackgroundColor = Color.White,
        dateInactiveBackgroundColor = Color.Black,
        dateActiveTextColor = Color.Black,
        dateInactiveTextColor = Color.White,
    )

    MaterialDialog(
        dialogState = dateDialogState,
        backgroundColor = colorResource(R.color.aqua),
        buttons = {
            positiveButton(
                "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = colorResource(R.color.teal_200),
                ),
                onClick = { onSelected(date) }
            )
//            this.button("time", onClick = {
//            })
            if (!isEdit) {
                negativeButton(
                    "Cancel",
                    textStyle = MaterialTheme.typography.button.copy(
                        color = colorResource(R.color.teal_200),
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
        }
    ) {
        datepicker(
            colors = colorTheme,
            initialDate = initialValue
        ) {
            date = it
        }
    }
}