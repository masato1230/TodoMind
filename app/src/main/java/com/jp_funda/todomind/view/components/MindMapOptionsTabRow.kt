package com.jp_funda.todomind.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R
import com.jp_funda.todomind.view.mind_map_create.options_sheet.MindMapOptionsMode

@Composable
fun MindMapOptionsTabRow(selectedMode: MindMapOptionsMode, onTabChange: (MindMapOptionsMode) -> Unit) {
    TabRow(
        modifier = Modifier.height(50.dp),
        selectedTabIndex = selectedMode.ordinal,
        backgroundColor = colorResource(id = R.color.transparent),
        contentColor = Color.LightGray,
    ) {
        Tab(
            selected = selectedMode == MindMapOptionsMode.ADD_CHILD,
            onClick = { onTabChange(MindMapOptionsMode.ADD_CHILD) }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mind_map),
                    contentDescription = "Edit"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("Add Child", style = MaterialTheme.typography.subtitle1)
            }
        }
        Tab(
            selected = selectedMode == MindMapOptionsMode.EDIT_TASK,
            onClick = { onTabChange(MindMapOptionsMode.EDIT_TASK) }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                Spacer(modifier = Modifier.width(10.dp))
                Text("Edit Task", style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}