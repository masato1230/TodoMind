package com.jp_funda.todomind.view.components

import android.content.res.Resources
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    dateDialogState: MaterialDialogState,
    resources: Resources,
    onSelected: (LocalDate) -> Unit,
) {
    var date: LocalDate = LocalDate.now()

    val colorTheme = DatePickerDefaults.colors(
        headerBackgroundColor = Color(resources.getColor(R.color.aqua)),
        headerTextColor = Color.White,
        activeBackgroundColor = Color.White,
        inactiveBackgroundColor = Color.Black,
        activeTextColor = Color.Black,
        inactiveTextColor = Color.White,
    )

    MaterialDialog(
        dialogState = dateDialogState,
        backgroundColor = Color(color = resources.getColor(R.color.aqua)),
        buttons = {
            positiveButton(
                "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = Color(resources.getColor(R.color.teal_200)),
                )
            )
//                this.button("time", onClick = {
//                    dateDialogState.hide()
//                    timeDialogState.show()
//                })
            negativeButton(
                "Cancel",
                textStyle = MaterialTheme.typography.button.copy(
                    color = Color(resources.getColor(R.color.teal_200)),
                )
            )
        }
    ) {
        datepicker(
            colors = colorTheme,
            // TODO set initial value
        ) { date ->

        }
    }
}