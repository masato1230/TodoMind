package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun TaskLists(listPadding: Int = 20) {
    Column {
        TaskTab()

        TaskList(listPadding = listPadding)
    }
}

@Composable
fun TaskList(listPadding: Int) {
    LazyColumn(modifier = Modifier.padding(horizontal = listPadding.dp)) {
        // todo fill with data
        items(items = List(10) { "d" }) { str ->
            TaskRow()
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}