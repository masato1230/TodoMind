package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R

@Composable
fun TaskTab() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    TabRow(
        selectedTabIndex = selectedTabIndex,
        backgroundColor = colorResource(id = R.color.deep_purple),
        contentColor = Color.White,
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = {
                // todo add filtering
                selectedTabIndex = 0
            },
            text = { Text("In Progress") }
        )
        Tab(
            selected = selectedTabIndex == 1,
            onClick = {
                // todo add filtering
                selectedTabIndex = 1
            },
            text = { Text("Open") }
        )
        Tab(
            selected = selectedTabIndex == 2,
            onClick = {
                // todo add filtering
                selectedTabIndex = 2
            },
            text = { Text("Closed") }
        )
    }
}
