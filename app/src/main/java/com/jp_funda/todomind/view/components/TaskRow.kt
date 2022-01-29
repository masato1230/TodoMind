package com.jp_funda.todomind.view.components

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.textview.MaterialTextView
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus

@Composable
fun TaskRow(
    task: Task,
    onCheckChanged: (task: Task) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.row_task, null)
        },
        update = { view ->
            view.findViewById<CheckBox>(R.id.row_task_checkbox)
                .setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        task.statusEnum = TaskStatus.Complete
                    } else {
                        task.statusEnum = TaskStatus.InProgress
                    }
                    onCheckChanged(task)
                }
            view.findViewById<MaterialTextView>(R.id.row_task_title).text = task.title
            view.findViewById<MaterialTextView>(R.id.row_task_description).text = task.description
            view.findViewById<MaterialTextView>(R.id.row_task_date).visibility = View.GONE
            view.findViewById<ImageView>(R.id.row_task_edit_button).setOnClickListener {
                // TODO navigate to edit task view
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    )
}