package com.jp_funda.todomind.view.components

import android.graphics.Paint
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
            // Initialize view
            val checkBox = view.findViewById<CheckBox>(R.id.row_task_checkbox)
            val title = view.findViewById<MaterialTextView>(R.id.row_task_title)
            val description = view.findViewById<MaterialTextView>(R.id.row_task_description)
            val date = view.findViewById<MaterialTextView>(R.id.row_task_date)
            val editButton = view.findViewById<ImageView>(R.id.row_task_edit_button)

            // Settings common to all statuses
            title.text = task.title
            description.text = task.description
            date.visibility = View.GONE
            editButton.setOnClickListener {
                // TODO navigate to edit task view
            }

            // Settings that vary depending on the status
            when (task.statusEnum) {
                // when status is Complete
                TaskStatus.Complete -> {
                    checkBox.isChecked = true
                    title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }
                // when status is Open or InProgress
                else -> {
                    checkBox.isChecked = false
                    title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }

            // Set listeners
            checkBox.setOnClickListener { view ->
                if ((view as CheckBox).isChecked) {
                    task.statusEnum = TaskStatus.Complete
                } else {
                    task.statusEnum = TaskStatus.InProgress
                }
                // update db with task instance
                onCheckChanged(task)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    )
}