package com.jp_funda.todomind.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jp_funda.todomind.R
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.view.MainViewModel
import com.jp_funda.todomind.view.task_detail.TaskEditableViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalMaterialApi
@Composable
fun TaskEditContent(
    modifier: Modifier = Modifier,
    taskEditableViewModel: TaskEditableViewModel,
    mainViewModel: MainViewModel?,
    onComplete: () -> Unit,
) {
    val context = LocalContext.current
    // Set up data
    val observedTask by taskEditableViewModel.task.observeAsState()
    val ogpResult by taskEditableViewModel.ogpResult.observeAsState()

    observedTask?.let { task ->
        // Set up dialogs
        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        val colorDialogState = rememberMaterialDialogState()
        DatePickerDialog(
            dateDialogState = dateDialogState,
            isEdit = task.dueDate != null,
            onReset = { taskEditableViewModel.resetDate() }
        ) { selectedDate ->
            taskEditableViewModel.setDate(selectedDate)
        }
        TimePickerDialog(
            timeDialogState = timeDialogState,
            isEdit = task.dueDate != null,
            onReset = { taskEditableViewModel.resetDate() }
        ) { selectedTime ->
            taskEditableViewModel.setTime(selectedTime)
        }
        ColorPickerDialog(colorDialogState) { selectedColor ->
            taskEditableViewModel.setColor(selectedColor.toArgb())
        }

        // Set up TextFields color
        val colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.White,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = colorResource(id = R.color.teal_200),
        )

        LaunchedEffect(ogpResult) {
            if (!task.description.isNullOrEmpty() && taskEditableViewModel.isShowOgpThumbnail) {
                taskEditableViewModel.extractUrlAndFetchOgp(task.description!!)
            }
        }

        Column(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Title TextField
            TextField(
                colors = colors,
                modifier = Modifier.fillMaxWidth(),
                value = task.title ?: "",
                onValueChange = taskEditableViewModel::setTitle,
                textStyle = MaterialTheme.typography.h6,
                placeholder = {
                    Text(
                        text = "Enter title",
                        color = Color.Gray,
                        style = MaterialTheme.typography.h6,
                    )
                },
            )

            // Description TextField
            val isLinkNode = task.styleEnum == NodeStyle.LINK
            TextField(
                colors = colors,
                modifier = Modifier.fillMaxWidth(),
                value = task.description ?: "",
                onValueChange = {
                    taskEditableViewModel.setDescription(it)
                    // do not check description contains url when isShowOgpThumbnail setting is off
                    if (taskEditableViewModel.isShowOgpThumbnail) {
                        taskEditableViewModel.extractUrlAndFetchOgp(it)
                    }
                },
                textStyle = MaterialTheme.typography.body1,
                placeholder = {
                    Text(
                        text = if (!isLinkNode) "Add description" else "Add Url",
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = if (!isLinkNode) R.drawable.ic_notes_24dp else R.drawable.ic_link_24),
                        contentDescription = "Description",
                        tint = Color.White
                    )
                })

            // OGP thumbnail
            ogpResult?.image?.let {
                OgpThumbnail(ogpResult = ogpResult!!, context = context)
            }

            // Date & Time
            Row(modifier = Modifier.fillMaxWidth()) {
                // Date
                TextField(
                    colors = colors,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clickable { dateDialogState.show() },
                    value = if (task.dueDate != null)
                        SimpleDateFormat(
                            "EEE MM/dd",
                            Locale.getDefault()
                        ).format(task.dueDate!!) else "",
                    onValueChange = {},
                    placeholder = {
                        Text("Add date/time", color = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            tint = Color.White,
                            contentDescription = "Date",
                        )
                    },
                    readOnly = true,
                    enabled = false,
                )

                // Time
                TextField(
                    colors = colors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            timeDialogState.show()
                        },
                    value = if (task.dueDate != null)
                        SimpleDateFormat(
                            "hh:mm aaa",
                            Locale.getDefault()
                        ).format(task.dueDate!!) else "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = "Add time", color = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_alarm_24dp),
                            tint = Color.White,
                            contentDescription = "Time",
                        )
                    },
                    readOnly = true,
                    enabled = false,
                )
            }

            // Color
            TextField(
                colors = colors,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { colorDialogState.show() },
                value = task.colorHex ?: "",
                onValueChange = {},
                placeholder = {
                    Text(text = "Set color", color = Color.Gray)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_color_24dp),
                        tint = task.color?.let { Color(it) }
                            ?: run { colorResource(id = R.color.teal_200) },
                        contentDescription = "Color",
                    )
                },
                readOnly = true,
                enabled = false,
            )

            // Style
            val styleOptions = NodeStyle.values()
            var styleExpanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = styleExpanded,
                onExpandedChange = {
                    styleExpanded = !styleExpanded
                }
            ) {
                TextField(
                    colors = colors,
                    value = "Style - ${task.styleEnum.title}",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_style_24),
                            contentDescription = "Style",
                            tint = task.color?.let { Color(it) }
                                ?: run { colorResource(id = R.color.teal_200) },
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = styleExpanded
                        )
                    },
                    readOnly = true,
                )
                ExposedDropdownMenu(
                    expanded = styleExpanded,
                    onDismissRequest = {
                        styleExpanded = false
                    }) {
                    styleOptions.forEach { option ->
                        DropdownMenuItem(onClick = {
                            taskEditableViewModel.setStyle(option)
                            styleExpanded = false
                        }) {
                            Text(text = option.title)
                        }
                    }
                }
            }

            // Status
            val statusOptions = TaskStatus.values()
            var expanded by remember { mutableStateOf(false) }
//            var selectedStatus by remember { mutableStateOf(statusOptions[0]) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    colors = colors,
                    value = "Status - ${task.statusEnum.name}",
                    onValueChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Status",
                            tint = Color.White
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    readOnly = true,
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }) {
                    statusOptions.forEach { option ->
                        DropdownMenuItem(onClick = {
                            taskEditableViewModel.setStatus(option)
                            expanded = false
                        }) {
                            Text(text = option.name)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Buttons
            Row(modifier = Modifier.fillMaxWidth()) {
                WhiteButton(
                    text = "OK",
                    leadingIcon = Icons.Default.Check,
                ) {
                    taskEditableViewModel.saveTask()
                    onComplete()
                }

                Spacer(modifier = Modifier.width(30.dp))

                if (taskEditableViewModel.isEditing) {
                    WhiteButton(
                        text = "Delete",
                        leadingIcon = Icons.Default.Delete,
                    ) {
                        // Delete task from DB(Edit mode) or Only Pop fragment(Create mode)
                        taskEditableViewModel.deleteTask(
                            task = task,
                            onSuccess = { onComplete() })
                        // Set CurrentlyDeletedTask at MainViewModel for undo snackbar
                        if (taskEditableViewModel.isEditing) {
                            mainViewModel?.currentlyDeletedTask = task
                        }
                    }
                }
            }
        }
    }
}