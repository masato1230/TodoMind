package com.jp_funda.todomind.view.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.color.ColorPalette
import com.vanpra.composematerialdialogs.color.colorChooser

@Composable
fun ColorPickerDialog(
    colorDialogState: MaterialDialogState,
    onColorSelected: (color: Color) -> Unit
) {
    var selectedColor = colorResource(R.color.teal_200)

    MaterialDialog(
        dialogState = colorDialogState,
        backgroundColor = colorResource(R.color.navy_blue),
        buttons = {
            positiveButton(
                "OK",
                textStyle = MaterialTheme.typography.button.copy(
                    color = colorResource(R.color.teal_200),
                ),
                onClick = {
                    onColorSelected(selectedColor)
                }
            )
            negativeButton(
                "Cancel",
                textStyle = MaterialTheme.typography.button.copy(
                    color = colorResource(R.color.teal_200),
                )
            )
        }
    ) {
        colorChooser(
            colors = ColorPalette.Primary,
            subColors = ColorPalette.PrimarySub,
            onColorSelected = { selectedColor = it })
    }
}