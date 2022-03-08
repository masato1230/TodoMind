package com.jp_funda.todomind.view.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.jp_funda.todomind.R

@Composable
fun NewTaskFAB(
    topBar: @Composable () -> Unit = {},
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "New task") },
                onClick = onClick,
                icon = { Icon(Icons.Filled.Add, "") }
            )
        },
        backgroundColor = colorResource(id = R.color.deep_purple)
    ) {
        content()
    }
}