package com.jp_funda.todomind.view.components

import android.content.res.Resources
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jp_funda.todomind.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.color.ColorPalette
import com.vanpra.composematerialdialogs.color.colorChooser

@Composable
fun ColorPickerDialog(
    colorDialogState: MaterialDialogState,
    resources: Resources,
    onColorSelected: (color: Color) -> Unit
) {
    MaterialDialog(
        dialogState = colorDialogState,
        backgroundColor = Color(color = resources.getColor(R.color.navy_blue)),
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
        }
    ) {
        colorChooser(
            colors = ColorPalette.Primary,
            subColors = ColorPalette.PrimarySub,
            onColorSelected = { onColorSelected(it) })
    }
}