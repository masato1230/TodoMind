package com.jp_funda.todomind.view.components

import android.content.res.ColorStateList
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.row_task, null)
        },
        update = { view ->
            // Initialize view
            val checkBox = view.findViewById<CheckBox>(R.id.row_task_checkbox)
            val mindMapIcon = view.findViewById<ImageView>(R.id.row_task_ic_mind_map)
            val mindMapLabel = view.findViewById<MaterialTextView>(R.id.row_task_label_mind_map)
            val title = view.findViewById<MaterialTextView>(R.id.row_task_title)
            val description = view.findViewById<MaterialTextView>(R.id.row_task_description)
            val date = view.findViewById<MaterialTextView>(R.id.row_task_date)

            // MindMapIcon & MindMapImage setting
            task.mindMap?.let { mindmap ->
                mindMapIcon.visibility = View.VISIBLE
                mindMapIcon.setColorFilter(mindmap.color ?: Color(R.color.crimson).toArgb())
                mindMapLabel.text = mindmap.title
                mindMapLabel.setTextColor(mindmap.color ?: Color(R.color.crimson).toArgb())
            } ?: run { mindMapIcon.visibility = View.GONE }

            // Settings : common to all statuses
            title.text = task.title
            description.text = task.description
            task.dueDate?.let {
                date.visibility = View.VISIBLE
                date.text = Task.dateFormat.format(task.dueDate!!)
            } ?: run {
                date.visibility = View.GONE
            }

            // Settings : that vary depending on the status
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

            // CheckBox settings
            checkBox.setOnClickListener {
                if ((it as CheckBox).isChecked) {
                    task.statusEnum = TaskStatus.Complete
                } else {
                    task.statusEnum = TaskStatus.InProgress
                }
                // update db with task instance
                onCheckChanged(task)
            }
            checkBox.buttonTintList = ColorStateList.valueOf(task.color ?: 0x0f03dac5).withAlpha(0xFF)
        },
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    )
}